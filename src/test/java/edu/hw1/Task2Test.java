package edu.hw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @Test
    @DisplayName("Проверка отрицательного числа")
    void test1() {
        int actual = Task2.countDigits(-10);
        int expected = 2*1;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Проверка нуля")
    void test2() {
        int actual = Task2.countDigits(0);
        int expected = 1*1;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Проверка положительного числа")
    void test3() {
        int actual = Task2.countDigits(10);
        int expected = 2;
        assertEquals(expected, actual);
    }
}


