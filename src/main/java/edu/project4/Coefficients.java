package edu.project4;

import java.util.concurrent.ThreadLocalRandom;

public record Coefficients(double a, double b, double c, double d, double e, double f) {

    private static boolean checkValid(double a, double b, double c, double d, double e, double f) {
        if ((a * a + d * d < 1) && (b * b + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d))) {
            return true;
        }
        return false;

    }

    public static Coefficients getCoefficients() {
        while (true) {
            double a = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double b = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double c = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double d = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double e = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double f = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            if (checkValid(a, b, c, d, e, f)) {
                return new Coefficients(a, b, c, d, e, f);
            }
        }
    }

}
