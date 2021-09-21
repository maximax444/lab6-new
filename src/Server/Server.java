package Server;

import common.Exceptions.ClosingSocketException;
import common.Exceptions.ConnectionErrorException;
import common.Exceptions.OpeningServerSocketException;
import common.MainConsole;
import common.Request;
import common.Response;
import common.Serialization;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private RequestIn requestIn;
    private Selector selector;

    public Server(int port, int soTimeout, RequestIn requestIn) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestIn = requestIn;
    }
    public class Connect {
        public byte[] byteArray = new byte[0];
        public double startTime = System.currentTimeMillis();

        public Connect() {

        }
    }
    /**
     * Begins server operation.
     */
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocketChannel.bind(new InetSocketAddress(port));
            MainConsole.println("Сервер начал слушать клиентов. ");
            MainConsole.println("Порт " + port + " / Адрес " + InetAddress.getLocalHost());
            MainConsole.println("Ожидаем подключения клиентов ");
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(true) {
                int count = selector.select();
                if (count == 0) {
                    continue;
                }
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();

                while(it.hasNext()) {
                    SelectionKey selectionKey = it.next();

                    if(selectionKey.isAcceptable()) {
                        acceptt(selectionKey);

                    } else if(selectionKey.isReadable()) {
                        read(selectionKey);

                    } else if(selectionKey.isWritable()) {
                        // a channel is ready for writing
                    }
                    it.remove();
                }
            }
           /* openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException exception) {
                    MainConsole.println(exception.getMessage());
                    break;
                } catch (IOException exception) {
                    MainConsole.println("Произошла ошибка при попытке завершить соединение с клиентом!");
                    MainServer.logger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();*/
        } catch (IOException exception) {
            MainConsole.println("Сервер не может быть запущен!");
            MainServer.logger.error("Сервер не может быть запущен!");
        }
    }
    private void acceptt(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = null;
        try {
            ServerSocketChannel acChannel = (ServerSocketChannel) selectionKey.channel();
            channel = acChannel.accept();
            MainConsole.println("Connection from: " + channel);

        } catch (IOException e) {
            MainConsole.println("Unable to accept channel");
            e.printStackTrace();
            selectionKey.cancel();
        }
        if (channel != null) {
            try {
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                MainConsole.println("Unable to use channel");
                e.printStackTrace();
                selectionKey.cancel();
            }
        }
    }
    private void read(SelectionKey selectionKey) throws IOException {
        if (selectionKey.attachment() == null) {
            selectionKey.attach(new Connect());
        }
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        /*if (socketChannel == null) {
            continue;
        }*/
        socketChannel.configureBlocking(false);
        Request cmd = null;
        try {
            cmd = CommandReceiver.getCommand(socketChannel, (Connect) selectionKey.attachment(), soTimeout);
        } catch (IOException e) {
            MainConsole.println("IOException " + socketChannel);
            selectionKey.cancel();


        } catch (TimeoutException e) {
            MainConsole.println("Disconnect " + socketChannel);
            selectionKey.cancel();
        }
        if (cmd != null) {
            MainConsole.println("Received command: " + cmd);

            /*try {
                requestIn.handle(cmd);

            } catch (Exception e) {
                MainConsole.println(e.getMessage());
            }*/
            socketChannel.write(ByteBuffer.wrap(Objects.requireNonNull(Serialization.SerializeObject(requestIn.handle(cmd)))));
            MainConsole.println("закрытие : " + selectionKey);
            selectionKey.cancel();
        }
    }

    /**
     * Finishes server operation.
     */
    /*private void stop() {
        try {
            if (serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            MainConsole.println("Работа сервера успешно завершена.");
        } catch (ClosingSocketException exception) {
            MainConsole.println("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            MainConsole.println("Произошла ошибка при завершении работы сервера!");
            MainServer.logger.error("Произошла ошибка при завершении работы сервера!");
        }
    }*/

    /**
     * Open server socket.
     */
    /*private void openServerSocket() throws OpeningServerSocketException {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
        } catch (IllegalArgumentException exception) {
            MainConsole.println("Порт '" + port + "' находится за пределами возможных значений!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            MainConsole.println("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }*/

    /**
     * Connecting to client.
     */
    /*private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            MainConsole.println("Прослушивание порта '" + port + "'...");
            MainServer.logger.info("Прослушивание порта '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            MainConsole.println("Соединение с клиентом успешно установлено.");
            MainServer.logger.info("Соединение с клиентом успешно установлено.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            MainConsole.println("Превышено время ожидания подключения!");
            MainServer.logger.error("Превышено время ожидания подключения!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            MainConsole.println("Произошла ошибка при соединении с клиентом!");
            MainServer.logger.error("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException("Произошла ошибка подключения!");
        }
    }*/

    /**
     * The process of receiving a request from a client.
     */
    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestIn.handle(userRequest);
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while (responseToUser.getResponseStatus() != 2);
            return false;
        } catch (ClassNotFoundException exception) {
            MainConsole.println("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {
            MainConsole.println("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                MainConsole.println("Непредвиденный разрыв соединения с клиентом!");
            } else {
                MainConsole.println("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }
}