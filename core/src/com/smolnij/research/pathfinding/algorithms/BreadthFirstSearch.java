package com.smolnij.research.pathfinding.algorithms;


import com.smolnij.research.pathfinding.Node;

import java.util.Set;

public class BreadthFirstSearch extends PathFinder {

    protected BreadthFirstSearch() {
        super(null);
    }

    @Override
    public void init(Node start, Node goal, Node[][] graph) {

    }

    @Override
    public boolean update(Set progress) {
        return false;
    }
}
