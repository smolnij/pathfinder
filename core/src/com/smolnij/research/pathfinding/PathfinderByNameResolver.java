package com.smolnij.research.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.algorithms.*;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

public class PathfinderByNameResolver {

    public static PathFinder getPathFinderByName(final String pathfinderName, final String heuristicName) {
        final Heuristic<StatefulGraphMapNode> heuristic = ImplementedHeuristics.createInstanceByName(heuristicName);
        switch (pathfinderName) {
            case AStarPathFinder.NAME:
                return new AStarPathFinder(heuristic);
            case BestFirstPathFinder.NAME:
                return new BestFirstPathFinder(heuristic);
            case BreadthFirstPathFinder.NAME:
                return new BreadthFirstPathFinder(null);
            default:
                throw new IllegalArgumentException("Algorithm " + pathfinderName + " not implemented");

        }
    }
}
