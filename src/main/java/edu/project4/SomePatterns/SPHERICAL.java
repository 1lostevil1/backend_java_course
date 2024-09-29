package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

public class SPHERICAL implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
        double rSquared = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / rSquared, point.y() / rSquared);
    }
}
