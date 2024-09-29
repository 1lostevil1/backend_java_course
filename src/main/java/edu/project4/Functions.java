package edu.project4;

import edu.project4.SomePatterns.DIAMOND;
import edu.project4.SomePatterns.DISC;
import edu.project4.SomePatterns.EXPONENTIAL;
import edu.project4.SomePatterns.HEART;
import edu.project4.SomePatterns.HYPERBOLIC;
import edu.project4.SomePatterns.POPCORN;
import edu.project4.SomePatterns.SINUSOIDAL;
import edu.project4.SomePatterns.SPIRAL;
import edu.project4.SomePatterns.WAVES;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Functions {

    private Functions() {
    }

    private static List<Function> functionList = List.of(
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new HEART(), new EXPONENTIAL())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new HEART(), new EXPONENTIAL(), new POPCORN(), new DISC())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new HEART(), new DIAMOND(), new WAVES())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new HEART(), new SPIRAL())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new SPIRAL(), new HYPERBOLIC())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new SINUSOIDAL(), new DISC(), new HEART())
        ),
        new Function(Coefficients.getCoefficients(), new Colour(),
            List.of(new DIAMOND(), new WAVES(), new HEART())
        )

    );

    public static Function getFunction() {
        return functionList.get(ThreadLocalRandom.current().nextInt(functionList.size()));
    }
}
