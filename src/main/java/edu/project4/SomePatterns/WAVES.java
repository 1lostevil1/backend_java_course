package edu.project4.SomePatterns;

import edu.project4.Coefficients;
import edu.project4.Fractal;
import edu.project4.Point;

public class WAVES implements Fractal {
    public Point apply(Coefficients coefficients, Point point) {
       return new Point(
            point.x() + coefficients.b() * Math.sin(point.y() / coefficients.c() * coefficients.c()),
            point.y() + coefficients.e() * Math.sin(point.x() / coefficients.f() * coefficients.f()));
    }
}
