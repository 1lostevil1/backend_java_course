package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

public class SINUSOIDAL implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
