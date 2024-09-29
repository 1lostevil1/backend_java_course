package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"MultipleStringLiterals", "RegexpSinglelineJava"})
public class Client implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BUFFER_SIZE = 1024;
    private SocketChannel socket;
    private static ByteBuffer buffer;

    public Client(String host, int port) throws IOException {
        socket = SocketChannel.open(new InetSocketAddress(host, port));
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public void sendToServer(String message) {
        try {
            buffer = ByteBuffer.wrap(message.getBytes());
            socket.write(buffer);
            buffer.clear();
            LOGGER.info("Ваня: " + message);
        } catch (IOException e) {
            LOGGER.warn(e);
        }
    }

    public void readFromServer() {
        try {
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.clear();
            socket.read(buffer);
            LOGGER.info("Сервер: " + new String(buffer.array()).trim());
            buffer.clear();
        } catch (IOException e) {
            LOGGER.warn(e);
        }
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

}
