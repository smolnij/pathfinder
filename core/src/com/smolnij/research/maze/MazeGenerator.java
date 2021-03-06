package com.smolnij.research.maze;

import com.smolnij.research.map.MapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    private Random random = new Random();
    private MazeCell[][] cells;
    private final int gridDimensionX, gridDimensionY;
    private final int mazeStepX, mazeStepY;
    private final MapNode[][] grid;

    public MazeGenerator(final int width, final int height) {
        this.mazeStepX = (width - 1) / 4;
        this.mazeStepY = (height - 1) / 2;

        gridDimensionX = width;
        gridDimensionY = height;

        grid = new MapNode[gridDimensionX][gridDimensionY];
    }

    private void init() {
        cells = new MazeCell[mazeStepX][mazeStepY];
        for (int x = 0; x < mazeStepX; x++) {
            for (int y = 0; y < mazeStepY; y++) {
                cells[x][y] = new MazeCell(x, y, false); // create cell (see Cell constructor)
            }
        }
    }


    public void generateMaze(final int startX, final int startY, final int endX, final int endY) {
        init(); //todo make end never blocked
        final MazeCell startAt = getCell(startX / 4, startY / 2);
        startAt.open = false;

        final MazeCell endAt = getCell(endX / 4, endY / 2);
        endAt.open = false;

        final List<MazeCell> cells = new ArrayList<>();
        cells.add(startAt);
        cells.add(endAt);

        while (!cells.isEmpty()) {
            MazeCell cell;
            // this is to reduce but not completely eliminate the number
            //   of long twisting halls with short easy to detect branches
            //   which results in easy mazes
            if (random.nextInt(10) == 0) {
                cell = cells.remove(random.nextInt(cells.size()));
            } else {
                cell = cells.remove(cells.size() - 1);
            }
            // for collection
            final List<MazeCell> neighbors = new ArrayList<>();
            // cells that could potentially be neighbors
            final MazeCell[] potentialNeighbors = new MazeCell[]{
                    getCell(cell.x + 1, cell.y),
                    getCell(cell.x, cell.y + 1),
                    getCell(cell.x - 1, cell.y),
                    getCell(cell.x, cell.y - 1)
            };
            for (final MazeCell other : potentialNeighbors) {
                // skip if outside, is a wall or is not opened
                if (other == null || other.wall || !other.open) continue;
                neighbors.add(other);
            }
            if (neighbors.isEmpty()) continue;

            final MazeCell selected = neighbors.get(random.nextInt(neighbors.size()));
            selected.open = false;
            cell.addNeighbor(selected);
            cells.add(cell);
            cells.add(selected);
        }
        updateGrid();
    }

    private MazeCell getCell(final int x, final int y) {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[0].length) {
            return null;
        }
        return cells[x][y];
    }

    public MapNode[][] getGrid() {
        return grid;
    }

    private void updateGrid() {
        for (int x = 0; x < gridDimensionX; x++) {
            for (int y = 0; y < gridDimensionY; y++) {
                grid[x][y] = new MapNode(x, y, false);
            }
        }

        for (int x = 0; x < gridDimensionX; x++) {
            for (int y = 0; y < gridDimensionY; y++) {
                if (x % 4 == 0 || y % 2 == 0)
                    grid[x][y] = new MapNode(x, y, true);
            }
        }

        for (int x = 0; x < mazeStepX; x++) {
            for (int y = 0; y < mazeStepY; y++) {
                MazeCell current = getCell(x, y);
                int gridX = x * 4 + 2, gridY = y * 2 + 1;

                grid[gridX][gridY] = new MapNode(gridX, gridY, false);
                if (current.isCellBelowNeighbor()) {
                    grid[gridX][gridY + 1] = new MapNode(gridX, gridY + 1, false);
                    grid[gridX + 1][gridY + 1] = new MapNode(gridX + 1, gridY + 1, false);
                    grid[gridX - 1][gridY + 1] = new MapNode(gridX - 1, gridY + 1, false);
                }
                if (current.isCellRightNeighbor()) {
                    grid[gridX + 2][gridY] = new MapNode(gridX + 2, gridY, false);
                    grid[gridX + 1][gridY] = new MapNode(gridX + 1, gridY, false);
                    grid[gridX + 3][gridY] = new MapNode(gridX + 3, gridY, false);
                }
            }
        }
    }
}
