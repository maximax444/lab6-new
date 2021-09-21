package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.CollectionIsEmptyException;
import common.Exceptions.ProductNotFoundException;
import common.Exceptions.WrongNumberOfElementsException;
import common.Task.Product;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }
    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (arg.isEmpty()) throw new WrongNumberOfElementsException("Должен присутствовать строковый аргумент");
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException("Коллекция уже пуста!");
            Long id = Long.parseLong(arg);
            Product productToRemove = collectionManager.getById(id);
            if (productToRemove == null) throw new ProductNotFoundException("Продукта с таким id нет в коллекции!");
            collectionManager.removeFromCollection(productToRemove);
            ResponseOut.println("Продукт успешно удален из коллекции!");
            return true;
        } catch (WrongNumberOfElementsException e) {
            ResponseOut.println(e.getMessage());
        } catch (CollectionIsEmptyException e) {
            ResponseOut.println(e.getMessage());
        } catch (ProductNotFoundException e) {
            ResponseOut.println(e.getMessage());
        }
        return false;
    }
}