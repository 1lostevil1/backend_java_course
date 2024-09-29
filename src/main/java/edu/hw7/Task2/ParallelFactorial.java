package edu.hw7.Task2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ParallelFactorial {

    private ParallelFactorial() {
    }

    public static BigInteger factorial(int value) {

        if (value <= 1) {
            return new BigInteger("1");
        }
        List<BigInteger> list = new ArrayList<>();
        for (int i = 2; i <= value; i++) {
            list.add(new BigInteger(String.valueOf(i)));
        }
        return list.parallelStream().reduce(new BigInteger("1"), BigInteger::multiply);
    }
}
