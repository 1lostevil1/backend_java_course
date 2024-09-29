package edu.hw8.Task1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({"MagicNumber", "uncommentedmain"})
public class Init {

    private Init() {
    }

    public static void main(String[] args) throws Exception {
        List<String> list = List.of("личности", "оскорбления", "глупый", "интеллект", "характер");

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

        String host = "localhost";
        Client client1 = new Client(host, 18080);
        Client client2 = new Client(host, 18080);
        client1.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client2.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client1.readFromServer();
        client2.readFromServer();
        Client client3 = new Client(host, 18080);
        Thread.sleep(100);
        client3.sendToServer(list.get(ThreadLocalRandom.current().nextInt(5)));
        client3.readFromServer();
        try {
            client1.close();
            thread.sleep(150);
            client2.close();
            thread.sleep(150);
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

