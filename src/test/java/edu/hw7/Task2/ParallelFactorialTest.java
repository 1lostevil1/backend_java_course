package edu.hw7.Task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

class ParallelFactorialTest {

    @Test
    @DisplayName("Факториал считается корректно")
    void test1() {
        // Given
        int num = 6;

        // Then
        assertEquals(new BigInteger("720"), ParallelFactorial.factorial(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    @DisplayName("Факториал считается корректно для чисел <1")
    void test2(int value) {
        // Given
        int num = value;

        // Then
        assertEquals(new BigInteger("1"), ParallelFactorial.factorial(num));
    }

    @Test
    @DisplayName("Факториал правильно считается даже для больших чисел")
    void test3()

    {
        // Given
        int num = 30;

        // Then
        assertEquals(new BigInteger("265252859812191058636308480000000"), ParallelFactorial.factorial(num));
    }

}
