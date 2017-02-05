package com.smolnij.research.pathfinding.algorithms;


import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

import java.util.Set;

public class BreadthFirstPathFinder extends PathFinder<StatefulGraphMapNode> {

    public static final String NAME = "Breadth First";

    public BreadthFirstPathFinder() {
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
