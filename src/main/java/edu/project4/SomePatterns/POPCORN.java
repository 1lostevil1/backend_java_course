package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

@SuppressWarnings("MagicNumber")

public class POPCORN implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
        return new Point(
            point.x() + coefficients.c() * Math.sin(Math.tan(3 * point.y())),
            point.y() + coefficients.f() * Math.sin(Math.tan(3 * point.x()))
        );
    }
}
