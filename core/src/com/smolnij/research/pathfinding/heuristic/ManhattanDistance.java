package com.smolnij.research.pathfinding.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.GridCoordinatesAware;

public class ManhattanDistance <N extends GridCoordinatesAware> implements Heuristic<N> {
    @Override
    public float estimate (final N node, final N endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }
}