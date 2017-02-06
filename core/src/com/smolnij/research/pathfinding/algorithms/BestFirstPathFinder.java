package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;
import com.smolnij.research.pathfinding.heuristic.GreedyNodeComparator;

import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstPathFinder extends PathFinder {
    public static final String NAME = "Greedy Best First";

    public BestFirstPathFinder(final Heuristic<StatefulGraphMapNode> heuristic) {
        super(heuristic);
    }

    @Override
    public Queue<StatefulGraphMapNode> getToBeEvaluatedQueueImplementation(final MapNode start, final MapNode goal) {
        return new PriorityQueue<>(new GreedyNodeComparator<>(this.goal, getHeuristic()));
    }
}
