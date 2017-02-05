package com.smolnij.research.scene;


import com.smolnij.research.pathfinding.GridCoordinatesAware;

public class TiledMapPoint implements GridCoordinatesAware { //todo get rid/unificate with pathnodegraph
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
