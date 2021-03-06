package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;
import com.smolnij.research.pathfinding.heuristic.AStarNodeComparator;

import java.util.PriorityQueue;
import java.util.Queue;

public class AStarPathFinder extends PathFinder {

    public static final String NAME = "A*";

    public AStarPathFinder(final Heuristic<StatefulGraphMapNode> heuristic) {
        super(heuristic);
    }

    @Override
    public Queue<StatefulGraphMapNode> getToBeEvaluatedQueueImplementation(final MapNode start, final MapNode goal) {
        return new PriorityQueue<>(new AStarNodeComparator<>(this.start, this.goal, getHeuristic()));
    }
}
