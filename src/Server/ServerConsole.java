package Server;


import Client.AskManager;
import common.Exceptions.CommandUsageException;
import common.Exceptions.ScriptRecursionException;
import common.Exceptions.WrongCommandException;
import common.MainConsole;
import common.ProductSer;
import common.Request;
import common.Task.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class ServerConsole {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public ServerConsole(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseStatus Last server's response status.
     * @return New request to server.
     */
    public Request handle(int serverResponseStatus) {
        String userInput;
        String[] userCommand = {"", ""};
        int processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseStatus == 0 || serverResponseStatus == 2)) {
                        throw new WrongCommandException("В скрипте есть некорректная команда!");
                    }

                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            MainConsole.println(userInput);
                        }
                    } else {
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    MainConsole.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        MainConsole.println("Превышено количество попыток ввода!");
                        System.exit(0);
                    }
                } catch (WrongCommandException e) {
                    MainConsole.println(e.getMessage());
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == 0 && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseStatus == 0 || processingCode == 0))
                    throw new WrongCommandException("В скрипте есть некорректная команда!");
                switch (processingCode) {
                    case 2:
                        ProductSer prod = generateProduct();
                        return new Request(userCommand[0], userCommand[1], prod);
                    case 3:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException("Скрипты не могут вызываться рекурсивно!");
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        MainConsole.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                MainConsole.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                MainConsole.println(exception.getMessage());
                throw new WrongCommandException("В скрипте есть некорректная команда!");
            }
        } catch (WrongCommandException exception) {
            MainConsole.println(exception.getMessage());
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request("","");
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private int processCommand(String commandName, String commandArgument) {
        try {
            switch (commandName) {
                case "":
                    return 0;
                case "help":
                case "show":
                case "info":
                case "save":
                case "clear":
                case "exit":
                case "history":
                case "min_by_manufacturer":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "remove_by_id":
                case "count_by_price":
                case "filter_starts_with_name":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                case "remove_greater":
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return 2;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return 2;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return 3;
                default:
                    MainConsole.println("Команда '" + commandName + "' не найдена!");
                    return 0;
            }
        } catch (CommandUsageException exception) {
            MainConsole.println("Проверьте введённые аргументы на правильность, проверка наличия/отсутствия аргументов вызвало ошибку!");
            return 0;
        }
        return 1;
    }

    /**
     * Generates Product to add.
     *
     * @return Product to add.
     * @throws WrongCommandException When something went wrong in script.
     */
    private ProductSer generateProduct() {
        AskManager askManager = new AskManager(userScanner);
        if (fileMode()) askManager.setFileMode();
        return new ProductSer(
                askManager.askName(),
                askManager.askCoordinates(),
                LocalDateTime.now(),
                askManager.askPrice(),
                askManager.askPartNumber(),
                askManager.askManufactureCost(),
                askManager.askUnitOfMeasure(),

                askManager.askOrgName(),
                askManager.askOrgFullName(),
                askManager.askAnnualTurnover(),
                askManager.askType(),
                askManager.askPostalAddress()

        );
    }



    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
