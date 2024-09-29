package edu.project2;

import java.util.Random;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("RegexpSinglelineJava")

public class Generate {

    private final static Logger LOGGER = LogManager.getLogger();

    public static Cell[][] mazeMatrix;

    public static int size;

    public Stack<Cell> coordStack;

    public Generate(int size) {
        this.size = size;
        coordStack = new Stack<>();
        mazeMatrix = new Cell[size][size];
    }

    public void print() {

        StringBuilder str = new StringBuilder();
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String white = "\u001B[37m";
        str.append(white + '\n' + reset);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mazeMatrix[i][j].isStartOrFinish) {
                    str.append(green + "!!!" + reset);
                    continue;
                }
                if (mazeMatrix[i][j].isWay) {
                    str.append(red + " * " + reset);
                    continue;
                }
                if (mazeMatrix[i][j].isWall) {
                    str.append("[=]");
                } else {
                    str.append(red + "   " + reset);
                }
            }
            str.append('\n');
        }
        LOGGER.info(str.toString());
    }

    public void maze() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((j % 2 != 0
                    && i % 2 != 0)
                    && (i < size - 1
                    && j < size - 1)) {
                    mazeMatrix[i][j] = new Cell(i, j, false, false, false, false);
                } else {
                    mazeMatrix[i][j] = new Cell(i, j, true, false, false, false);
                }
            }
        }
    }

    public void removeWall(Cell first, Cell second) {
        int xDiff = second.x - first.x;
        int yDiff = second.y - first.y;
        int addX;
        int addY;

        addX = (xDiff != 0) ? (xDiff / Math.abs(xDiff)) : 0;
        addY = (yDiff != 0) ? (yDiff / Math.abs(yDiff)) : 0;

        mazeMatrix[first.x + addX][first.y + addY].isWall = false;
        mazeMatrix[first.x + addX][first.y + addY].isVisited = true;
    }

    public void makeWay(Cell first, Cell second, boolean reverse) {
        int xDiff;
        xDiff = second.x - first.x;
        int yDiff;
        yDiff = second.y - first.y;
        int addX;
        int addY;

        addX = (xDiff != 0) ? (xDiff / Math.abs(xDiff)) : 0;
        addY = (yDiff != 0) ? (yDiff / Math.abs(yDiff)) : 0;

        if (!reverse) {
            mazeMatrix[first.x + addX][first.y + addY].isWay = true;
        } else {
            mazeMatrix[first.x + addX][first.y + addY].isWay = false;
        }
    }

    public int unvisitedCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mazeMatrix[i][j].isVisited && !mazeMatrix[i][j].isWall) {
                    count++;
                }
            }
        }
        return count;
    }

    public void makeUnvisited() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mazeMatrix[i][j].isVisited) {
                    mazeMatrix[i][j].isVisited = false;
                }
            }
        }
    }

    public void wayFound(Cell start, Cell finish) throws Exception {

        if (mazeMatrix[start.x][start.y].isWall || mazeMatrix[finish.x][finish.y].isWall) {
            throw new Exception("!!CELL IS WALL!!");
        }

        makeUnvisited();
        mazeMatrix[start.x][start.y].isVisited = true;
        mazeMatrix[start.x][start.y].isWay = true;
        mazeMatrix[start.x][start.y].isStartOrFinish = true;
        mazeMatrix[finish.x][finish.y].isStartOrFinish = true;
        coordStack.clear();
        Random random;
        int randNum;
        Neighbours neighbours;
        coordStack.push(start);
        Cell currentCell = start;
        Cell neighbourCell;
        while (currentCell.x != finish.x || currentCell.y != finish.y) {
            neighbours = new Neighbours(currentCell);
            neighbours.notIgnoreWallBetween(currentCell);
            if (neighbours.size != 0) {
                random = new Random();
                randNum = random.nextInt(0, neighbours.size);
                neighbourCell = neighbours.neighboursList.get(randNum);
                coordStack.push(neighbourCell);
                makeWay(currentCell, neighbourCell, false);
                currentCell = neighbourCell;
                mazeMatrix[currentCell.x][currentCell.y].isVisited = true;
                mazeMatrix[currentCell.x][currentCell.y].isWay = true;
            } else if (coordStack.size() > 0) {
                mazeMatrix[coordStack.peek().x][coordStack.peek().y].isWay = false;
                makeWay(coordStack.pop(), coordStack.peek(), true);
                currentCell = mazeMatrix[coordStack.peek().x][coordStack.peek().y];
            }

        }

    }

    public boolean gen3000() {
        maze();
        mazeMatrix[1][1].isVisited = true;
        Cell startCell = mazeMatrix[1][1];
        Cell currentCell = startCell;
        coordStack.push(currentCell);
        Neighbours neighbours;
        Cell neighbourCell;
        Random random;
        int randNum;
        do {
            neighbours = new Neighbours(currentCell);
            if (neighbours.size != 0) {
                random = new Random();
                randNum = random.nextInt(0, neighbours.size);
                neighbourCell = neighbours.neighboursList.get(randNum);
                coordStack.push(neighbourCell);
                removeWall(currentCell, neighbourCell);
                currentCell = neighbourCell;
                mazeMatrix[currentCell.x][currentCell.y].isVisited = true;
            } else if (coordStack.size() > 0) {
                coordStack.pop();
                if (coordStack.isEmpty()) {
                    print();
                    return false;
                }
                currentCell = mazeMatrix[coordStack.peek().x][coordStack.peek().y];
            }

        } while (unvisitedCount() > 0);
        coordStack.clear();
        return true;
    }
}
