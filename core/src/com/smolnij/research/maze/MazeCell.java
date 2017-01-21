package com.smolnij.research.maze;

import java.util.ArrayList;
import java.util.List;

public class MazeCell {
    public final int x;
    public final int y;
    public final boolean wall;
    public boolean open = true;
    List<MazeCell> neighbors = new ArrayList<>();

    public MazeCell(int x, int y, boolean wall) {
        this.x = x;
        this.y = y;
        this.wall = wall;
    }


    // add a neighbor to this cell, and this cell as a neighbor to the other
    void addNeighbor(MazeCell other) {
        if (!this.neighbors.contains(other)) { // avoid duplicates
            this.neighbors.add(other);
        }
        if (!other.neighbors.contains(this)) { // avoid duplicates
            other.neighbors.add(this);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return wall;
    }

    // used in updateGrid()
    boolean isCellBelowNeighbor() {
        return this.neighbors.contains(new MazeCell(this.x, this.y + 1, false));
    }

    // used in updateGrid()
    boolean isCellRightNeighbor() {
        return this.neighbors.contains(new MazeCell(this.x + 1, this.y, false));
    }

    @Override
    public boolean equals(final Object otherMazeCell) {
        if (this == otherMazeCell) return true;
        if (otherMazeCell == null || getClass() != otherMazeCell.getClass()) return false;

        MazeCell mazeCell = (MazeCell) otherMazeCell;

        return x == mazeCell.x && y == mazeCell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
