package com.smolnij.research.pathfinding.algorithms;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;
import com.smolnij.research.pathfinding.heuristic.ManhattanDistance;

public enum ImplementedHeuristics {

    MANHATTAN(Names.MANHATTAN_NAME), CHEBYSHEV(Names.CHEBYSHEV_NAME), EUCLIDIAN(Names.EUCLIDIAN_NAME);

    private final String name;

    ImplementedHeuristics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Heuristic<StatefulGraphMapNode> createInstanceByName(final String heuristicName) {
        switch (heuristicName) {
            case Names.MANHATTAN_NAME:
                return new ManhattanDistance<>();
            case Names.CHEBYSHEV_NAME:
                return new ManhattanDistance<>();
            case Names.EUCLIDIAN_NAME:
                return new ManhattanDistance<>();
            default:
                throw new IllegalArgumentException("Unknown heuristic: " + heuristicName);
        }
    }

    private static class Names {
        public static final String MANHATTAN_NAME = "Manhattan";
        public static final String CHEBYSHEV_NAME = "Chebyshev";
        public static final String EUCLIDIAN_NAME = "Euclidian";
    }
}
