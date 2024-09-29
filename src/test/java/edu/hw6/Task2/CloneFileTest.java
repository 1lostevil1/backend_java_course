package edu.hw6.Task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

import edu.hw6.Task2.CloneFile;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CloneFileTest {
    private final static String DIRECTORY_PATH = "TestFile";

    public static void deleteDirectory(File directory) throws Exception {
        if (!directory.exists()) {
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

    @BeforeEach
    void createCloneFIle() throws Exception {
        Files.createDirectory(Path.of(DIRECTORY_PATH));
    }

    @AfterEach
    void deleteAllFiles() throws Exception {
        deleteDirectory(new File(DIRECTORY_PATH));
    }

    @Test
    @DisplayName("Ошибка при попытке копирования несуществующего файна")
    void NoSuchFile() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "test.txt");
        //expect
        assertThrows(IOException.class, () -> CloneFile.cloneFile(path));
    }

    @Test
    @DisplayName("Первое успешное клонирование")
    void firstClone() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "test.txt");
        //when
        Files.createFile(path);
        Files.writeString(path, "test");

        CloneFile.cloneFile(path);

        Path newPath = Path.of(DIRECTORY_PATH, "test - копия.txt");
        //then
        assertThat(Files.exists(newPath)).isTrue();
        assertThat(Files.readString(newPath)).isEqualTo("test");
    }

    @Test
    @DisplayName("Более одного успешного клонирования")
    void nClone() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "test.txt");
        //when
        Files.createFile(path);
        CloneFile.cloneFile(path);
        CloneFile.cloneFile(path);
        //then
        assertThat(Files.exists(Path.of(DIRECTORY_PATH, "test - копия (2).txt"))).isTrue();
    }

    @Test
    @DisplayName("Работа без расширений")
    void cloneWithNoExtension() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "test");
        //when
        Files.createFile(path);
        CloneFile.cloneFile(path);
        //then
        assertThat(Files.exists(Path.of(DIRECTORY_PATH, "test - копия"))).isTrue();
    }
}
