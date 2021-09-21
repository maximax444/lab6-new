package Server.Commands;

import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;


public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        super("save","save collection to file");
        this.collectionManager = collectionManager;
    }
    /**
     * Executes the command
     * @return exit status of command
     * **/
    public boolean startExecute(String arg, Object o){
        try {
            if (!arg.isEmpty()) throw new WrongNumberOfElementsException("У данной команды не должно быть аргумента!");
            collectionManager.saveCollection();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
