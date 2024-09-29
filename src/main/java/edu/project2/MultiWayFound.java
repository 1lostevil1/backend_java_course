package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("MagicNumber")

public class MultiWayFound extends RecursiveTask<List<Cell>> {

    private static Cell[][] mazeMatrix;

    private static int size;

    private Stack<Cell> coordStack;

    private Cell start;
    private Cell end;

    public MultiWayFound(Stack<Cell> coordStack, Cell[][] mazeMatrix, Cell start, Cell end) {
        this.mazeMatrix = mazeMatrix;
        this.coordStack = coordStack;
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Cell> compute() {
        return solve(start, end);
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

    public void makeUnvisited() {
        for (int i = 0; i < Generate.size; i++) {
            for (int j = 0; j < Generate.size; j++) {
                if (mazeMatrix[i][j].isVisited) {
                    mazeMatrix[i][j].isVisited = false;
                }
            }
        }
    }

    private void forkaction(List<Cell> neighboursList, Cell currentCell, List<Cell> path) {
        if (neighboursList.size() == 1) {
            coordStack.push(currentCell);
            makeWay(currentCell, neighboursList.get(0), false);
            path.add(currentCell);
            mazeMatrix[currentCell.x][currentCell.y].isWay = true;
            mazeMatrix[currentCell.x][currentCell.y].isVisited = true;
        } else {
            List<MultiWayFound> classes = new ArrayList<>(neighboursList.size());

            classes.stream().forEach(obj -> {
                new MultiWayFound(
                    coordStack,
                    mazeMatrix,
                    new Cell(neighboursList.get(0).x, neighboursList.get(0).y, false, true, true, false),
                    end
                );
                obj.fork();
                path.addAll(obj.join());
            });

        }
    }

    public List<Cell> solve(Cell start, Cell finish) {
        List<Cell> answer = new ArrayList();
        makeUnvisited();
        mazeMatrix[start.x][start.y].isVisited = true;
        mazeMatrix[start.x][start.y].isWay = true;
        mazeMatrix[start.x][start.y].isStartOrFinish = true;
        mazeMatrix[finish.x][finish.y].isStartOrFinish = true;
        Neighbours neighbours;
        coordStack.push(start);
        answer.add(start);
        Cell currentCell = start;
        while (currentCell.x != finish.x || currentCell.y != finish.y) {
            neighbours = new Neighbours(currentCell);
            neighbours.notIgnoreWallBetween(currentCell);
            if (neighbours.size != 0) {
                forkaction(neighbours.neighboursList, currentCell, answer);
            }

            if (neighbours.size == 0) {
                mazeMatrix[coordStack.peek().x][coordStack.peek().y].isWay = false;
                answer.remove(answer.indexOf(coordStack.peek()));
                makeWay(coordStack.pop(), coordStack.peek(), true);
                currentCell = mazeMatrix[coordStack.peek().x][coordStack.peek().y];
            }
        }
        return answer;

    }

    private List<Integer> directions(Cell currentCell, Neighbours neighbours) {
        List<Integer> answer = new ArrayList<>();

        for (var neighbour : neighbours.neighboursList) {
            if (isUpDirection(currentCell, neighbour)) {
                answer.add(1);
            }
            if (isDownDirection(currentCell, neighbour)) {
                answer.add(2);
            }
            if (isLeftDirection(currentCell, neighbour)) {
                answer.add(3);
            }
            if (isRightDirection(currentCell, neighbour)) {
                answer.add(4);
            }
        }

        return answer;
    }

    private boolean isUpDirection(Cell currentCell, Cell neighbour) {
        return currentCell.x >= 3
            && neighbour.x < currentCell.x;
    }

    private boolean isDownDirection(Cell currentCell, Cell neighbour) {
        return currentCell.x < Generate.size - 3
            && neighbour.x > currentCell.x;
    }

    private boolean isLeftDirection(Cell currentCell, Cell neighbour) {
        return currentCell.y >= 3
            && neighbour.y < currentCell.y;
    }

    private boolean isRightDirection(Cell currentCell, Cell neighbour) {
        return currentCell.y < Generate.size - 3
            && neighbour.y > currentCell.y;
    }

}
