package edu.project4;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("MagicNumber")

public class Colour {

    private int red;
    private int green;
    private int blue;

    public Colour() {
        red = ThreadLocalRandom.current().nextInt(256);
        green = ThreadLocalRandom.current().nextInt(256);
        blue = ThreadLocalRandom.current().nextInt(256);
    }

    public Colour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getRGB() {
        return (red << 16 | green << 8 | blue);
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
