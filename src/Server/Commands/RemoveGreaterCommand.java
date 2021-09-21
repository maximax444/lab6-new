package Server.Commands;



import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.EmptyCollectionException;
import common.Exceptions.WrongNumberOfElementsException;
import common.ProductSer;
import common.Task.Organization;
import common.Task.Product;


public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public RemoveGreaterCommand(CollectionManager collectionManager){
        super("remove_greater","removes all elements that more than specified");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public boolean startExecute(String arg, Object o) {

        try{
            if (!arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("У данной команды должен быть объектный аргумент!");
            if(collectionManager.collectionSize() == 0) throw new EmptyCollectionException("Коллекция пуста!");
            ProductSer ps = (ProductSer) o;
            Product prod = new Product(
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
            collectionManager.removeGreater(prod);
            ResponseOut.println("Продукты успешно удалены!");
            return true;
        } catch (EmptyCollectionException e){
            ResponseOut.println(e.getMessage());
            return false;
        } catch (WrongNumberOfElementsException e) {
            ResponseOut.println(e.getMessage());
            return false;
        }

    }

}
