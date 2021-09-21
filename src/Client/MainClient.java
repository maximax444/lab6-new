package Client;


import common.Exceptions.IncorrectInputException;
import common.Exceptions.WrongNumberOfElementsException;
import common.MainConsole;

import java.util.Scanner;

public class MainClient {
    private static String host = "localhost";
    public static int port = 3578;
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Должен быть введён порт!");
            System.exit(0);
        }
        port = Integer.parseInt(args[0]);

        if(!initializeConnectionAddress(host, port)) return;

        Scanner userScanner = new Scanner(System.in);

        ClientConsole clientConsole = new ClientConsole(userScanner);
        Client client = new Client(host, port, clientConsole);
        client.startWork();
        userScanner.close();
    }
    private static boolean initializeConnectionAddress(String hostt, int portt) {
        try {
            host = hostt;
            port = portt;
            if (port < 0) throw new IncorrectInputException("Порт не может быть отрицательным!");
            return true;
        } catch (NumberFormatException exception) {
            MainConsole.println("Порт должен быть представлен числом!");
        } catch (IncorrectInputException exception) {
            MainConsole.println(exception.getMessage());
        }
        return false;
    }
}
