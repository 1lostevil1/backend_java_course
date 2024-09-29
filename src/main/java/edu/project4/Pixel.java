package edu.project4;

public class Pixel {

    private Colour rgb;
    private int countHit;
    private double normal;

    public Pixel() {
        normal = 0;
        countHit = 0;
        rgb = new Colour(0, 0, 0);
    }

    public Pixel(int countHit, Colour rgb) {
        this.countHit = countHit;
        this.rgb = rgb;
    }

    public void incrementCountHit() {
        countHit++;
    }

    public Colour getColor() {
        return rgb;
    }

    public double getNormal() {
        return normal;
    }

    public void setColor(Colour rgb) {
        this.rgb = rgb;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public int getCountHit() {
        return countHit;
    }

}

