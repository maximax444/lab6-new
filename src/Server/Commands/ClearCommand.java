package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;

public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");
            collectionManager.clearCollection();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
