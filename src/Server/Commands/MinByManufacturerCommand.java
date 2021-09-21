package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.EmptyCollectionException;
import common.Exceptions.WrongNumberOfElementsException;
import common.Task.Product;

import java.util.Set;

public class MinByManufacturerCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public MinByManufacturerCommand(CollectionManager collectionManager) {
        super("min_by_manufacturer", "вывести любой объект из коллекции, значение поля manufacturer которого является минимальным");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        
        try{
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");
            if(collectionManager.collectionSize() == 0) throw new EmptyCollectionException("Коллекция пуста");
        } catch (EmptyCollectionException e){
            System.out.println(e.getMessage());
            return false;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        Set<Product> coll = collectionManager.getCollection();
        Product prodMinMan = null;
        int i = 0;
        for (Product prod : coll) {
            if (i == 0) {
                prodMinMan = prod;
                i++;
            }
            if (prodMinMan.getMan() - prod.getMan() < 0) {
                prodMinMan = prod;
            }
        }
        ResponseOut.println(prodMinMan.toString());

        
        return true;
        
    }
}
