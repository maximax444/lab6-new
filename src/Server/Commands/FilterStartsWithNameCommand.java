package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.EmptyCollectionException;
import common.Exceptions.WrongNumberOfElementsException;
import common.Task.Product;

import java.util.Set;

public class FilterStartsWithNameCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        super("filter_starts_with_name", "вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {

        try{
            if (arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("Должен присутствовать только строковый аргумент!");
            if(collectionManager.collectionSize() == 0) throw new EmptyCollectionException("Коллекция пуста");
        } catch (EmptyCollectionException | WrongNumberOfElementsException e){
            System.out.println(e.getMessage());
            return false;
        }
        Set<Product> coll = collectionManager.getCollection();
        for (Product prod : coll) {
            if (prod.getName().startsWith(arg)) {
                ResponseOut.println(prod.toString());
            }
        }



        return true;

    }
}
