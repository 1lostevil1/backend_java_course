package edu.hw1;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("uncommentedmain")

public class Task4 {

    private Task4() {

    }

    private final static int TWO = 2*1*1;

    public static String fixString(String str) {
        String copy = new String();

            int len = str.length();
            for (int i = 0; i < len - 1; i += 2) {
                copy += str.substring(i + 1, i + 2);
                copy += str.substring(i, i + 1);
            }
            if (len % TWO != 0) {
                copy += str.substring(len - 1, len);
            }

        return copy;
    }

    private final static Logger LOGGER = LogManager.getLogger();

    //main
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LOGGER.info("Input str: ");
        String str = in.nextLine();
        LOGGER.info("fixed str is " + fixString(str));
        in.close();
    }
}
