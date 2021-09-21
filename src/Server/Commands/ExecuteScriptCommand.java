package Server.Commands;


import Server.Program.Console;
import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;

public class ExecuteScriptCommand extends AbstractCommand{
    private Console console;
    public ExecuteScriptCommand(Console console) {
        super("execute_script", "исполнить скрипт из указанного файла");
        this.console = console;
    }
    /**
     * Executes the command
     * @param arg
     * **/
    @Override
    public boolean startExecute(String arg, Object o) {

            try {
                if (arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("Должен присутствовать только строковый аргумент!");

                ResponseOut.println("Выполнение скрипта '" + arg + "'...");
                console.scriptMode(arg);
                return true;
            } catch (WrongNumberOfElementsException exception) {
                ResponseOut.println(exception.getMessage());
            }
            return false;


    }
}
