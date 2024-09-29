package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task3.Filter.regexContains;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterTest {
    public static final Path DIRECTORY = Path.of("src/main/java/edu/hw6/Task3/files");

    @Test
    @DisplayName("Проверка фильтра largerThan")
    void test1() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = Filter.largerThan(5);
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Проверка фильтра globMatches")
    void test2() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/1.txt, src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = Filter.globMatches("(.*).txt");
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("Проверка фильтра magicNumber")
    void test3() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = Filter.magicNumber('1', '2', '3');
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Проверка фильтра regexContains")
    void test4() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = regexContains("2");
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Проверка фильтра READABLE")
    void test5() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/3.dox, src/main/java/edu/hw6/Task3/files/1.txt" +
                ", src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = Filter.READABLE;
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("API - цепочечный")
    void test6() {
        // given
        List<Path> list = new ArrayList<>();
        List<Path> expected = List.of(
            Path.of("src/main/java/edu/hw6/Task3/files/2.txt")
        );

        // when
        DirectoryStream.Filter<Path> filter = Filter
            .globMatches("(.*).txt")
            .and(regexContains("2"));
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(DIRECTORY, filter)) {
            for (var entry : entries) {
                list.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertEquals(expected, list);
    }
}
