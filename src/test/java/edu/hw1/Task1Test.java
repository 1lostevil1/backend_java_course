package edu.hw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    @Test
    @DisplayName("Минуты без секунд ")
    void test1() {
        int actual = Task1.minutesToSeconds("22:");
        int expected = -1;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Секунды без минут")
    void test2() {
        int actual = Task1.minutesToSeconds(":22");
        int expected = -1;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Корректный ввод")
    void test3() {
        int actual = Task1.minutesToSeconds("01:00");
        int expected = 60;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Число не в формате mm:ss")
    void test4() {
        int actual = Task1.minutesToSeconds("1:00");
        int expected = -1;
        assertEquals(expected, actual);

        assertEquals(expected, actual);
        actual = Task1.minutesToSeconds("01:1");
        expected = -1;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Пустая строка")
    void test5() {
        int actual = Task1.minutesToSeconds(" ");
        int expected = -1;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Отрицательные числа")
    void test6() {
        int actual = Task1.minutesToSeconds("-01:00");
        int expected = -1;
        assertEquals(expected, actual);

        actual = Task1.minutesToSeconds("01:-01");
        expected = -1;
        assertEquals(expected, actual);

        actual = Task1.minutesToSeconds("-01:-01");
        expected = -1;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Два двоеточия")
    void test7() {
        int actual = Task1.minutesToSeconds("11::11");
        int expected = -1;
        assertEquals(expected, actual);
    }

}
