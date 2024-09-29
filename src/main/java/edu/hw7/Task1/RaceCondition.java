package edu.hw7.Task1;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RaceCondition {
    private static final int MAX = 100;
    private static final  Logger LOGGER = LogManager.getLogger();
    private static AtomicInteger value = new AtomicInteger(0);

    private RaceCondition() {
    }

    public static int increment() {
        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < MAX; i++) {
                value.incrementAndGet();
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < MAX; i++) {
                value.incrementAndGet();
            }
        });

        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();

        } catch (
            InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        return value.get();

    }
}
