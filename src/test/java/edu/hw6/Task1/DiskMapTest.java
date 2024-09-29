package edu.hw6.Task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DiskMapTest {
    private final static String DIRECTORY_PATH = "test_disk_map";

    @BeforeEach
    @AfterEach
    void deleteIfExist() throws Exception {
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
    @DisplayName("Успешное создание мапы")
    void successfulConstructor() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //expect
        assertThat(map).isNotNull();
    }

    @Test
    @DisplayName("Директория уже существует")
    void existDirectory() throws IOException {
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //expect
        assertThat(map).isNotNull();
    }

    @Test
    @DisplayName("Существует файл, бросок ошибки")
    void existFile() {
        //given
        try {
            Files.createFile(Path.of(DIRECTORY_PATH));
            //expect
            assertThrows(
                IllegalArgumentException.class,
                () -> new DiskMap(DIRECTORY_PATH)
            );
        } catch (IOException e) {
            assertThat(e).isNull();
        }
    }

    @Test
    @DisplayName("Успешно добавляет значения")
    void successPut() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        map.put("test", "test");
        //then
        Path test = Path.of(DIRECTORY_PATH, "test");
        assertTrue(Files.exists(test));
        assertThat(Files.readString(test)).isEqualTo("test");
    }

    @Test
    @DisplayName("Файл уже существует")
    void putToExistingFile() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path test = Path.of(DIRECTORY_PATH, "test");
        //when
        Files.createDirectories(test.getParent());
        Files.createFile(test);
        map.put("test", "test");
        //then
        assertTrue(Files.exists(test));
        assertThat(Files.readString(test)).isEqualTo("test");
    }

    @Test
    @DisplayName("Удаление файла")
    void removeFile() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path test = Path.of(DIRECTORY_PATH, "test");
        //when
        Files.createDirectories(test.getParent());
        Files.createFile(test);
        //then
        assertThat(Files.exists(test)).isTrue();
        //when
        map.remove("test");
        //then
        assertThat(Files.exists(test)).isFalse();
    }

    @Test
    @DisplayName("Удаление несуществующего файла")
    void removeNonExistingFile() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path test = Path.of(DIRECTORY_PATH, "test");
        //expect
        assertThat(map.remove("test")).isNull();
        assertThat(Files.exists(test)).isFalse();
    }

    @Test
    @DisplayName("Количество ключей")
    void getSize() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(Path.of(DIRECTORY_PATH, "first"));
        Files.createFile(Path.of(DIRECTORY_PATH, "second"));
        Files.createFile(Path.of(DIRECTORY_PATH, "third"));
        //expect
        assertThat(map.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Количество ключей при отсутствии файлов")
    void getZeroSize() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThat(map.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Проверка на пустоту")
    void isEmpty() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThat(map.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Проверка на отсутствие пустоты")
    void isNotEmpty() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(Path.of(DIRECTORY_PATH, "first"));
        Files.createFile(Path.of(DIRECTORY_PATH, "second"));
        Files.createFile(Path.of(DIRECTORY_PATH, "third"));
        //then
        assertThat(map.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Ключ существует")
    void keyExist() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(Path.of(DIRECTORY_PATH, "test"));
        //then
        assertThat(map.containsKey("test")).isTrue();
    }

    @Test
    @DisplayName("Ключ не существует")
    void keyNotExist() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThat(map.containsKey("test")).isFalse();
    }

    @Test
    @DisplayName("Ключ не строка")
    void keyNotString() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThrows(
            IllegalArgumentException.class,
            () -> map.containsKey(List.of())
        );
    }

    @Test
    @DisplayName("Значение существует")
    void valueExist() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path first = Path.of(DIRECTORY_PATH, "first");
        Path second = Path.of(DIRECTORY_PATH, "second");
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(first);
        Files.createFile(second);
        Files.writeString(first, "first");
        Files.writeString(second, "second");
        //then
        assertThat(map.containsValue("first")).isTrue();
    }

    @Test
    @DisplayName("Значение не существует")
    void valueNotExist() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThat(map.containsValue("test")).isFalse();
    }

    @Test
    @DisplayName("Значение не строка")
    void valueNotString() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThrows(
            IllegalArgumentException.class,
            () -> map.containsValue(List.of())
        );
    }

    @Test
    @DisplayName("Получение значения по ключу")
    void getByKey() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path first = Path.of(DIRECTORY_PATH, "first");
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(first);
        Files.writeString(first, "first");
        //then
        assertThat(map.get("first")).isEqualTo("first");
    }

    @Test
    @DisplayName("Получение по ключу, значения нет")
    void getNull() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        //then
        assertThat(map.get("test")).isNull();
    }

    @Test
    @DisplayName("Успешно добавляет значения")
    void successPutAll() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Map<String, String> putValues = Map.of(
            "first", "first",
            "second", "second"
        );

        map.putAll(putValues);

        Path first = Path.of(DIRECTORY_PATH, "first");
        //then
        assertTrue(Files.exists(first));
        assertThat(Files.readString(first)).isEqualTo("first");
        //when
        Path second = Path.of(DIRECTORY_PATH, "second");
        //then
        assertTrue(Files.exists(second));
        assertThat(Files.readString(second)).isEqualTo("second");
    }

    @Test
    @DisplayName("Файлы уже существует")
    void putAllToExistingFiles() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Map<String, String> putValues = Map.of(
            "first", "first",
            "second", "second"
        );
        Path first = Path.of(DIRECTORY_PATH, "first");
        Path second = Path.of(DIRECTORY_PATH, "second");

        Files.createDirectories(first.getParent());
        Files.createFile(first);
        Files.createFile(second);

        map.putAll(putValues);
        //then
        assertTrue(Files.exists(first));
        assertThat(Files.readString(first)).isEqualTo("first");

        assertTrue(Files.exists(second));
        assertThat(Files.readString(second)).isEqualTo("second");
    }

    @Test
    @DisplayName("Чистка папки")
    void clear() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        //when
        Files.createDirectories(Path.of(DIRECTORY_PATH));
        Files.createFile(Path.of(DIRECTORY_PATH, "first"));
        Files.createFile(Path.of(DIRECTORY_PATH, "second"));
        Files.createFile(Path.of(DIRECTORY_PATH, "third"));
        map.clear();
        //then
        assertThat(Files.list(Path.of(DIRECTORY_PATH)).count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Получение списка ключей")
    void getKeys() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path first = Path.of(DIRECTORY_PATH, "first");
        Path second = Path.of(DIRECTORY_PATH, "second");
        //when
        Files.createDirectories(first.getParent());
        Files.createFile(first);
        Files.createFile(second);
        //then
        assertThat(map.keySet()).isEqualTo(Set.of("first", "second"));
    }

    @Test
    @DisplayName("Получение списка значений")
    void getValues() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path first = Path.of(DIRECTORY_PATH, "first");
        Path second = Path.of(DIRECTORY_PATH, "second");
        //when
        Files.createDirectories(first.getParent());
        Files.createFile(first);
        Files.createFile(second);

        Files.writeString(first, "first");
        Files.writeString(second, "second");
        //then
        assertThat(map.values().stream().sorted().toList()).isEqualTo(List.of("first", "second"));
    }

    @Test
    @DisplayName("Получение сущностей")
    void getEntrySet() throws IOException {
        //given
        DiskMap map = new DiskMap(DIRECTORY_PATH);
        Path first = Path.of(DIRECTORY_PATH, "first");
        Path second = Path.of(DIRECTORY_PATH, "second");
        //when
        Files.createDirectories(first.getParent());
        Files.createFile(first);
        Files.createFile(second);

        Files.writeString(first, "first");
        Files.writeString(second, "second");
        //then
        assertThat(map.entrySet()).isEqualTo(Map.of(
            "first", "first",
            "second", "second"
        ).entrySet());
    }
}
