package Server.Commands;


import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.IncorrectInputException;
import common.Exceptions.WrongNumberOfElementsException;
import common.ProductSer;
import common.Task.Organization;
import common.Task.Product;

import java.util.Set;

public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", " id {element} обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("ДОлжны быть аргументы");
            Set<Product> coll = collectionManager.getCollection();
            Long argLong = Long.parseLong(arg);
            Product prod = collectionManager.getById(argLong);
            if(prod == null) throw new IncorrectInputException("Продукта с таким id нет!");
            ProductSer ps = (ProductSer) o;
            Product prod2 = new Product(
                    collectionManager.generateNextId(),
                    ps.getName(),
                    ps.getCoordinates(),
                    ps.getLDT(),
                    ps.getPrice(),
                    ps.getPartNumber(),
                    ps.getManufactureCost(),
                    ps.getUnitOfMeasure(),
                    new Organization(
                            (int) (long) collectionManager.generateNextId(),
                            ps.getOrgName(),
                            ps.getOrgFullName(),
                            ps.getAnnualTurnover(),
                            ps.getType(),
                            ps.getPostalAddress()
                    )
            );
            collectionManager.removeFromCollection(prod);
            collectionManager.addToCollection(prod2);
            ResponseOut.println("Продукт успешно обновлен!");
            return true;
        } catch (WrongNumberOfElementsException | IncorrectInputException exception) {
            ResponseOut.println(exception.getMessage());
        } catch (NumberFormatException e){
            ResponseOut.println("Аргумент должен быть числом!");
            return false;
        }
        return false;
    }
}
