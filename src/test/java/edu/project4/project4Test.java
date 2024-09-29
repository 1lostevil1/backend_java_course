package edu.project4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class project4Test {

    @Test
    @DisplayName("render меняет изображение")
    void test1() {
        //given
        FractalFlame flame = new FractalFlame(5000, 1000, 10, true, 1.0);
        boolean expected = false;
        //when
        flame.render();
        for (int i = 0; i < 1920 && !expected; i++) {
            for (int j = 0; j < 1080 && !expected; j++) {
                if (flame.getImage()[i][j].getCountHit() != 0) {
                    expected = true;
                }
            }
        }
        //then
        assertTrue(expected);
    }

    @Test
    @DisplayName("gammaCorrection меняет изображение")
    void test2() {
        //given
        FractalFlame flame = new FractalFlame(5000, 100, 10, true, 2.3);
        flame.render();
        Pixel[][] tmpFlame = new Pixel[1920][1080];
        for (int i = 0; i < 1920; i++) {
            for (int j = 0; j < 1080; j++) {
                tmpFlame[i][j] = new Pixel(flame.getImage()[i][j].getCountHit(), flame.getImage()[i][j].getColor());
            }
        }
        //when
        flame.gammaCorrection();
        boolean expected = false;
        for (int i = 0; i < 1920 && !expected; i++) {
            for (int j = 0; j < 1080 && !expected; j++) {
                if ((flame.getImage()[i][j].getColor().getRed() != tmpFlame[i][j].getColor().getRed())) {
                    expected = true;
                }
            }
        }
        //then
        assertTrue(expected);
    }
}
