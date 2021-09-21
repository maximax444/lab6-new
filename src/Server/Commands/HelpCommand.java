package Server.Commands;


import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
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
