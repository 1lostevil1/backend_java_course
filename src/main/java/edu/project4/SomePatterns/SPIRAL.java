package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

public class SPIRAL implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan(point.x() / point.y());
        return new Point((Math.cos(theta) + Math.sin(r)) / r, (Math.sin(theta) - Math.cos(r)) / r);
    }
}
