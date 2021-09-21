package Server.Program;

import java.io.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import common.Task.Product;

public class FileManager {
    private Gson serializer = new Gson();
    private String commandArg;

    public FileManager(String commandArg) {
        this.commandArg = commandArg;
    }

    /**
     * Reads collection from a JSON-file.
     * @return Readed collection.
     */
    public TreeSet<Product> readCollection() {

        if (commandArg != null) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(commandArg))) {
                Type collectionType = new TypeToken<TreeSet<Product>>() {}.getType();
                int symbol = inputStreamReader.read();
                String data = "";
                while(symbol != -1) {
                    data = data.concat(String.valueOf((char) symbol).trim());
                    symbol = inputStreamReader.read();
                }
                Console.println(data);
                TreeSet<Product> collection = serializer.fromJson(data, collectionType);
                Console.println("Коллекция успешна загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                Console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                Console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                Console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else Console.printError("Системная переменная с загрузочным файлом не найдена!");
        return new TreeSet<Product>();
    }

    /**
     * Write collection to a JSON-file.
     * @param collection Collection to write.
     */
    public void writeCollection(Collection<?> collection) {

            try(FileOutputStream out = new FileOutputStream(commandArg);
                BufferedOutputStream bos = new BufferedOutputStream(out))
            {
                String text = serializer.toJson(collection);
                // перевод строки в байты
                byte[] buffer = text.getBytes();
                bos.write(buffer);
                Console.println("Коллекция успешна сохранена в файл!");
            }
            catch(JsonParseException | IOException ex){
                Console.printError("Загрузочный файл является директорией/не может быть открыт!");
            }
    }


}
