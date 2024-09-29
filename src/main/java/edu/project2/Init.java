package edu.project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("uncommentedmain")

public class Init {

    private Init() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        final int SIZE = 7;
        final int x1 = 1;
        final int y1 = 1;
        final int x2 = 5;
        final int y2 = 5;
        Generate a = new Generate(SIZE);
        a.gen3000();

        Cell start = new Cell(x1, y1, false, true, true, false);
        Cell finish = new Cell(x2, y2, false, false, false, false);

        a.print();
        MultiWayFound tmp = new MultiWayFound(a.coordStack, a.mazeMatrix, start, finish);

        var list = tmp.compute();
        LOGGER.info(list);

    }
}
