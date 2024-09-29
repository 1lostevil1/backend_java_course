package edu.hw6.Task4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

import edu.hw6.Task4.StreamChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StreamChainTest {
    private final static String DIRECTORY_PATH = "test_chain";

    @BeforeEach
    void createCloneFIle() throws Exception {
        deleteDirectory(new File(DIRECTORY_PATH));
        Files.createDirectory(Path.of(DIRECTORY_PATH));
    }

    @AfterEach
    void deleteAllFiles() throws Exception {
        deleteDirectory(new File(DIRECTORY_PATH));
    }

    public static void deleteDirectory(File directory) throws Exception {
        if(!directory.exists()) {
            return;
        }

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }

        // Удаляем саму директорию
        if (!directory.delete()) {
            throw new Exception("Не удалось удалить файл");
        }
    }

    @Test
    void testChain() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "chain.txt");
        //when
        StreamChain.streamsChain(path);
        //when
        assertThat(Files.readString(path)).isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");
    }
}
