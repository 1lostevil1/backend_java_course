package edu.hw7.Task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonteKarloPiTest {

    @Test
    @DisplayName("Отклонение от числа pi незначительно")
    void test1() {
        // Given
        long numberOfIterations = 100_000_000;
        double delta = 0.001;

        // Then
        assertTrue(Math.abs(MonteKarloPi.parallelPiCalculate(numberOfIterations) - Math.PI) <delta);
        assertTrue(Math.abs(MonteKarloPi.parallelPiCalculate(numberOfIterations) - Math.PI) < delta);
    }
}
