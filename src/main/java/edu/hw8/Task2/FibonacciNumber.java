package edu.hw8.Task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("RegexpSinglelineJava")
public class FibonacciNumber implements Runnable {

    private final int num;

    public FibonacciNumber(int num) {
        this.num = num;
    }

    private final static Logger LOGGER = LogManager.getLogger();

    public long findFibonacciNumber(int num) {
        if (num < 1) {
            return 0;
        }
        if (num <= 2) {
            return 1;
        }
        return findFibonacciNumber(num - 1) + findFibonacciNumber(num - 2);
    }

    @Override
    public void run() {
        LOGGER.info("[" + Thread.currentThread().getName() + "] looking for the " + num + " Fibonacci number");
        LOGGER.info("[" + Thread.currentThread().getName() + "] result = " + findFibonacciNumber(num));
    }
}
