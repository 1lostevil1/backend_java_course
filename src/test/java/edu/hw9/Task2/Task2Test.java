package edu.hw9.Task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Task2Test {

    private Path tempDir = Files.createTempDirectory(
        Path.of("src/test/java/edu/hw9/Task2/Directory"),
        "dir_"
    );

    private List<String> extensions = List.of(".txt", ".json", ".doc");

    public Task2Test() throws IOException {
    }

    private void createDir(Path tempDir, int level) throws IOException {
        if (level < 3) {
            Path tempDirectory = tempDir;
            for (int i = 0; i < 2000; ++i) {
                if (ThreadLocalRandom.current().nextInt(1000) == 999) {
                    tempDirectory = Files.createTempDirectory(tempDirectory, "dir" + i + "_");
                    level++;
                    createDir(tempDirectory, level);
                } else {
                    File.createTempFile("00" + i + "_", extensions.get(ThreadLocalRandom.current()
                        .nextInt(3)), tempDirectory.toFile()).deleteOnExit();
                }
            }
        }
    }

    @Test
    @DisplayName("Поиск директорий, в которых больше 1000 файлов")
    void test1() throws IOException {
        // given
        createDir(tempDir, 0);
        List<Path> answer = new ArrayList<>();
        FindDirectory directoryWithMoreThanThousandFiles =
            new  FindDirectory(Path.of("src/test/java/edu/hw9/Task2/Directory"), answer);
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // when
        forkJoinPool.invoke(directoryWithMoreThanThousandFiles);

        // then
        assertFalse(answer.isEmpty());
        forkJoinPool.close();
    }

    @Test
    @DisplayName("Поиск файлов, с заданным размером и расширением")
    void test2() throws IOException {
        // given
        createDir(tempDir, 2);
        List<Path> answer = new ArrayList<>();
        long size = 512;
        String extension = "txt";
        FindFile filesWithSpecifiedSizeAndExtension =
            new FindFile(
                size,
                extension,
                answer,
                Path.of("src/test/java/edu/hw9/Task2/Directory")
            );
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // when
        forkJoinPool.invoke(filesWithSpecifiedSizeAndExtension);

        // then
        assertFalse(answer.isEmpty());
        forkJoinPool.close();
    }
}
