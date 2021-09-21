package Server.Program;



import Client.AskManager;
import Server.ResponseOut;
import common.Exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    private CommandManager commandManager;
    private Scanner userScanner;
    private AskManager askManager;
    private int consoleStatus;
    private List<String> scriptStack = new ArrayList<>();

    public Console(Scanner userScanner, AskManager askManager) {
        this.userScanner = userScanner;
        this.askManager = askManager;
        this.consoleStatus = 0;
    }
    /**
     * Sets the console
     * **/
    public void setCommandManager(CommandManager commandManager){
        this.commandManager = commandManager;
    }
    /**
     * Mode for catching commands from console.
     */
    public void interactiveMode() {
        String[] userInput = {"", ""};
        consoleStatus = 1;
        try {
            do {
                Console.println("Введите команду:");
                userInput = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userInput[1] = userInput[1].trim();
                commandManager.startExecuteCommand(userInput[0], userInput[1], null);
            } while (consoleStatus != 0);
        } catch (NoSuchElementException exception) {
            Console.printError("Ввод некорректен!");
        } catch (IllegalStateException exception) {
            Console.printError("Ошибка!");
        }
    }
    /**
     * Mode for catching commands from file.
     */
    public void scriptMode(String arg) {
        String[] userInput = {"", ""};
        consoleStatus = 2;
        Boolean commandStatus;
        scriptStack.add(arg);

        try (Scanner scriptScanner = new Scanner(new File(arg))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();

            Scanner tmpScanner = askManager.getUserScanner();
            askManager.setUserScanner(scriptScanner);
            askManager.setFileMode();
            do {
                userInput = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userInput[1] = userInput[1].trim();
                while (scriptScanner.hasNextLine() && userInput[0].isEmpty()) {
                    userInput = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userInput[1] = userInput[1].trim();
                }
                ResponseOut.println(userInput[0] + " " + userInput[1]);

                if (userInput[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userInput[1].equals(script)) throw new ScriptRecursionException("Рекурсия скрипта недопустима!");
                    }
                }
                commandStatus = commandManager.startExecuteCommand(userInput[0], userInput[1], null);
            } while (commandStatus == true && scriptScanner.hasNextLine());
            askManager.setUserScanner(tmpScanner);
            askManager.setUserMode();
            if (commandStatus == false && !(userInput[0].equals("execute_script") && !userInput[1].isEmpty()))
                Console.println("Проверьте скрипт на корректность введенных данных!");
        } catch (FileNotFoundException exception) {
            Console.println("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            Console.println("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            Console.println("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            Console.println("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
    }

    public int getConsoleStatus() {
        return consoleStatus;
    }

    /**
     * Exit console
     */
    public void exit() {
        consoleStatus = 0;
        Console.println("Работа завершена");
    }


    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error in Program
     * @param err Error
     */
    public static void printError(Object err) {
        System.out.println("Возникла ошибка: " + err);
    }
}
