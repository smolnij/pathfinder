package com.smolnij.research.pathfinding.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.GridCoordinatesAware;

public class ChebyshevHeuristic<N extends GridCoordinatesAware> implements Heuristic<N> {

    @Override
    public float estimate(final N a, final N b) {
        return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }
}
