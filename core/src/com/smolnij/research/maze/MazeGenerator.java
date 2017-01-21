package com.smolnij.research.maze;

import com.smolnij.research.pathfinding.Node;

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
    private Random random = new Random(); // The random object
    private MazeCell[][] cells; // 2d array of Cells
    private final int gridDimensionX, gridDimensionY;
    private final int mazeStepX, mazeStepY;
    private Node[][] grid;

    public MazeGenerator(int mazeStepX, int mazeStepY) {
        this.mazeStepX = mazeStepX;
        this.mazeStepY = mazeStepY;
        gridDimensionX = mazeStepX * 4 + 1;
        gridDimensionY = mazeStepY * 2 + 1;
        System.out.println(gridDimensionX);
        System.out.println(gridDimensionY);

        grid = new Node[gridDimensionX][gridDimensionY];
        init();
    }

    private void init() {
        // create cells
        cells = new MazeCell[mazeStepX][mazeStepY];
        for (int x = 0; x < mazeStepX; x++) {
            for (int y = 0; y < mazeStepY; y++) {
                cells[x][y] = new MazeCell(x, y, false); // create cell (see Cell constructor)
            }
        }
    }


    public void generateMaze(int x, int y) {
        MazeCell startAt = getCell(x, y);
        // don't generate from cell not there
        if (startAt == null) return;
        startAt.open = false; // indicate cell closed for generation
        ArrayList<MazeCell> cells = new ArrayList<>();
        cells.add(startAt);

        while (!cells.isEmpty()) {
            MazeCell cell;
            // this is to reduce but not completely eliminate the number
            //   of long twisting halls with short easy to detect branches
            //   which results in easy mazes
            if (random.nextInt(10) == 0)
                cell = cells.remove(random.nextInt(cells.size()));
            else cell = cells.remove(cells.size() - 1);
            // for collection
            ArrayList<MazeCell> neighbors = new ArrayList<>();
            // cells that could potentially be neighbors
            MazeCell[] potentialNeighbors = new MazeCell[]{
                    getCell(cell.x + 1, cell.y),
                    getCell(cell.x, cell.y + 1),
                    getCell(cell.x - 1, cell.y),
                    getCell(cell.x, cell.y - 1)
            };
            for (MazeCell other : potentialNeighbors) {
                // skip if outside, is a wall or is not opened
                if (other == null || other.wall || !other.open) continue;
                neighbors.add(other);
            }
            if (neighbors.isEmpty()) continue;
            // get random cell
            MazeCell selected = neighbors.get(random.nextInt(neighbors.size()));
            // add as neighbor
            selected.open = false; // indicate cell closed for generation
            cell.addNeighbor(selected);
            cells.add(cell);
            cells.add(selected);
        }
    }


    // used to get a Cell at x, y; returns null out of bounds
    public MazeCell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
            return null;
        }
    }


    public static void main(String[] args) {
        MazeGenerator mg = new MazeGenerator(12, 14);
        mg.generateMaze(6, 5);
        mg.updateGrid();


        for (int i = 0; i < 49; i++) {
            for (int j = 0; j < 29; j++) {
                System.out.print(mg.getGrid()[i][j].isBlocked() ? '+' : '*');
            }
            System.out.println();
        }
    }

    public Node[][] getGrid() {
        return grid;
    }

    public void updateGrid() {
        for (int x = 0; x < gridDimensionX; x++) {
            for (int y = 0; y < gridDimensionY; y++) {
                grid[x][y] = new Node(x, y, false);
            }
        }

        // build walls
        for (int x = 0; x < gridDimensionX; x++) {
            for (int y = 0; y < gridDimensionY; y++) {
                if (x % 4 == 0 || y % 2 == 0)
                    grid[x][y] = new Node(x, y, true);
            }
        }
        // make meaningful representation
        for (int x = 0; x < mazeStepX; x++) {
            for (int y = 0; y < mazeStepY; y++) {
                MazeCell current = getCell(x, y);
                int gridX = x * 4 + 2, gridY = y * 2 + 1;

                grid[gridX][gridY] = new Node(gridX, gridY, false);
                if (current.isCellBelowNeighbor()) {
                    grid[gridX][gridY + 1] = new Node(gridX, gridY + 1, false);
                    grid[gridX + 1][gridY + 1] = new Node(gridX + 1, gridY + 1, false);
                    grid[gridX - 1][gridY + 1] = new Node(gridX - 1, gridY + 1, false);
                }
                if (current.isCellRightNeighbor()) {
                    grid[gridX + 2][gridY] = new Node(gridX + 2, gridY, false);
                    grid[gridX + 1][gridY] = new Node(gridX + 1, gridY, false);
                    grid[gridX + 3][gridY] = new Node(gridX + 3, gridY, false);
                }
            }
        }

    }
}
