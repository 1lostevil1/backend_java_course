package edu.hw9.Task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class CollectorTest {

    private final List<String> metricNames = List.of(
        "a", "b", "c", "d", "e");
    private final Logger logger = LogManager.getLogger();

    @Test
    @DisplayName("Программа справляется с задачей")
    void test1() throws InterruptedException {
        //given
        int collectorSize = 3;
        int countOfMetrics = 5;
        Collector collector = new Collector(collectorSize);
        //when
        var send = Stream.generate(() -> CompletableFuture.runAsync(() ->
            {
                collector.push(
                    metricNames.get(ThreadLocalRandom.current().nextInt(5)),
                    new double[] {ThreadLocalRandom.current().nextDouble(1, 5),
                        ThreadLocalRandom.current().nextDouble(1, 5),
                        ThreadLocalRandom.current().nextDouble(1, 5),
                        ThreadLocalRandom.current().nextDouble(1, 5)}
                );
            }, Executors.newFixedThreadPool(collectorSize)))
            .limit(countOfMetrics)
            .toArray(CompletableFuture[]::new);
        logger.info("name        summ                avg                max                min");
        var answer = Stream.generate(() -> CompletableFuture.runAsync(
                () ->
                {
                    try {
                        logger.info(collector.collect());
                    } catch (InterruptedException e) {
                        logger.warn(e);
                    }
                },
                Executors.newFixedThreadPool(collectorSize)
            ))
            .limit(countOfMetrics)
            .toArray(CompletableFuture[]::new);
        //expect
        CompletableFuture.allOf(send).join();
        CompletableFuture.allOf(answer).join();
    }
}
