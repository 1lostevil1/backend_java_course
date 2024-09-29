package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Task1Test {

    @Test
    @DisplayName("сервер и клиенты общается")
    public void test() throws Exception {

        //given
        List<String> list = List.of("личности", "оскорбления", "глупый", "интеллект", "характер");

        //when
        Server server = new Server(18080, 5);
        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        Thread.sleep(1000);

        Client client1 = new Client("localhost", 18080);
        thread.sleep(150);
        Client client2 = new Client("localhost", 18080);
        thread.sleep(150);
        client1.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client1.readFromServer();
        thread.sleep(150);
        client2.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client2.readFromServer();
        Client client3 = new Client("localhost", 18080);
        Thread.sleep(100);
        client3.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client3.readFromServer();
        //expect
        try {
            client1.close();
            thread.sleep(100);
            client2.close();
            thread.sleep(100);
            client3.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(1000);
        try {
            server.close();
            thread.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

