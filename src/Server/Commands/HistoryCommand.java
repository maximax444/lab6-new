package Server.Commands;

import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;

public class HistoryCommand extends AbstractCommand{
    public HistoryCommand(){
        super("history","вывести последние 8 введенных команд без аргументов");
    }
    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");

            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}