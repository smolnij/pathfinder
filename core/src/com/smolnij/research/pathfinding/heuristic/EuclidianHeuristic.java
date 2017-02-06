package com.smolnij.research.pathfinding.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.GridCoordinatesAware;

public class EuclidianHeuristic<N extends GridCoordinatesAware> implements Heuristic<N> {

    @Override
    public float estimate(final N a, final N b) {
        double x = Math.pow(a.getX() - b.getX(), 2.0);
        double y = Math.pow(a.getY() - b.getY(), 2.0);

        return (float) Math.sqrt(x + y);
    }
}
