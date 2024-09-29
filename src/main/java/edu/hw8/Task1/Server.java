package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.nio.charset.StandardCharsets.UTF_8;


@SuppressWarnings("MagicNumber")
public class Server implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BUFFER_SIZE = 1024;
    private final ServerSocketChannel serverSocket;
    private final BlockingQueue<SocketChannel> blockingQueue;
    private final Selector selector;
    private final ExecutorService threads;
    private final ByteBuffer buffer;

    public Server(int port, int countOfThreads) throws IOException {
        blockingQueue = new LinkedBlockingDeque<>(countOfThreads);
        threads = Executors.newFixedThreadPool(countOfThreads);
        serverSocket = ServerSocketChannel.open();
        selector = Selector.open();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public void start() throws IOException, InterruptedException {
        while (selector.isOpen()) {
            selector.select();
            if (selector.isOpen()) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (!key.isValid()) {
                        iter.remove();
                        blockingQueue.poll();
                        continue;
                    }

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        threads.submit(() -> {
                            try {
                                answer(buffer, key);
                            } catch (IOException e) {
                               LOGGER.warn(e);
                            }
                        });
                        Thread.sleep(1000);
                    }
                }
            }
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocket)
        throws IOException, InterruptedException {
        SocketChannel client = serverSocket.accept();
        if (client != null) {
            blockingQueue.put(client);
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        }

    }

    private void answer(ByteBuffer buffer, SelectionKey key)
        throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        int check = client.read(buffer);
        buffer.flip();
        if (check != -1) {
            client.write(getAnswer(buffer));
        }
        buffer.clear();
        client.close();
        blockingQueue.remove(client);
    }

    private ByteBuffer getAnswer(ByteBuffer byteBuffer) {
        String currentData = UTF_8.decode(byteBuffer).toString();
        String answer = "Error\n";
        if (ReplyMap.REPLIES.containsKey(currentData)) {
            answer = ReplyMap.REPLIES.get(currentData) + '\n';
        }
        return ByteBuffer.wrap(answer.getBytes(UTF_8));
    }

    @Override public void close() throws Exception {
        threads.shutdown();
        serverSocket.close();
        selector.close();
    }
}
