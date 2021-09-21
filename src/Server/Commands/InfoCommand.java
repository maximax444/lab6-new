package Server.Commands;



import Server.Program.CollectionManager;
import Server.ResponseOut;
import common.Exceptions.WrongNumberOfElementsException;

import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }

    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty()) throw new WrongNumberOfElementsException("Команда: '" + getName() + "' должна использоваться без аргументов");
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeAns = "";
            if (lastInitTime == null) {
                lastInitTimeAns = "no init";
            } else {
                lastInitTimeAns = lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            }


            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeAns = "";
            if (lastSaveTime == null) {
                lastSaveTimeAns = "Сохранения не было";
            } else {
                lastSaveTimeAns = lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
            }

            ResponseOut.println("Сведения о коллекции:");
            ResponseOut.println(" - Тип: " + collectionManager.collectionType());
            ResponseOut.println(" - Количество элементов: " + collectionManager.collectionSize());
            ResponseOut.println(" - Дата последней инициализации: " + lastInitTimeAns);
            ResponseOut.println(" - Дата последнего сохранения: " + lastSaveTimeAns);
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
