package com.smolnij.research.map;


import com.smolnij.research.pathfinding.GridCoordinatesAware;

public class TiledMapPoint implements GridCoordinatesAware {
    public int x;
    public int y;

    public TiledMapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
