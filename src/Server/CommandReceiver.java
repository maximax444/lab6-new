package Server;

import common.Request;
import common.Serialization;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeoutException;

public class CommandReceiver {
    /**
     * @param socketChannel SocketChannel из которого нужно получить команду
     * @return Считанная команда
     * @throws TimeoutException Исключение при превышении времени ожидания
     */
    public static Request getCommand(SocketChannel socketChannel, Server.Connect connect, int timeout) throws IOException, TimeoutException {
        byte[] resultBytes = connect.byteArray;
        byte[] bytes = new byte[100000];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Request response = null;
        int numberOfBytesRead = 1;

        numberOfBytesRead = socketChannel.read(byteBuffer);
        if (numberOfBytesRead < 0) {
            throw new SocketException();
        }
        byte[] tempBytes = new byte[resultBytes.length + numberOfBytesRead];
        System.arraycopy(resultBytes, 0, tempBytes, 0, resultBytes.length);
        System.arraycopy(bytes, 0, tempBytes, resultBytes.length, numberOfBytesRead);
        resultBytes = tempBytes;
        connect.byteArray = resultBytes;
        byteBuffer.clear();
        try {
            response = (Request) Serialization.DeserializeObject(resultBytes);
            return response;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }
    }
}