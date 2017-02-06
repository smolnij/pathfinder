package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPathFinder extends PathFinder {

    public static final String NAME = "Breadth First";

    public BreadthFirstPathFinder(final Heuristic<StatefulGraphMapNode> heuristic) {
        super(heuristic);
    }


    @Override
    public Queue<StatefulGraphMapNode> getToBeEvaluatedQueueImplementation(final MapNode start, final MapNode goal) {
        return new LinkedList<>();
    }

}
