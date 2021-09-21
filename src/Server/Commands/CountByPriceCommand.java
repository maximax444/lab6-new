package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;


public class CountByPriceCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public CountByPriceCommand(CollectionManager collectionManager) {
        super("count_by_price", "вывести количество элементов, значение поля price которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("Должен присутствовать только строковый аргумент");
            int argInt = Integer.parseInt(arg);
            ResponseOut.println(collectionManager.countByPrice(argInt));
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
