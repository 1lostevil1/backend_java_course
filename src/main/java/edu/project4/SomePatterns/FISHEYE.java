package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

public class FISHEYE implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        return new Point(2 * point.y() / (r + 1), 2 * point.x() / (r + 1));
    }
}
