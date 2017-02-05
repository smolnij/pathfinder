package com.smolnij.research.pathfinding.algorithms;


import com.smolnij.research.pathfinding.MapNode;

import java.util.Set;

public class BreadthFirstSearch extends PathFinder {

    protected BreadthFirstSearch() {
        super(null);
    }

    @Override
    public void init(MapNode start, MapNode goal, MapNode[][] graph) {

    }

    @Override
    public boolean update(Set progress) {
        return false;
    }
}
