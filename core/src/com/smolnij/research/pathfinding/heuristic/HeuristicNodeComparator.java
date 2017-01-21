package com.smolnij.research.pathfinding.heuristic;

import com.smolnij.research.pathfinding.Node;

import java.util.Comparator;

public class HeuristicNodeComparator<T>/*<T extends TileCoordinatesAware> implements Comparator<T> */
        implements Comparator<Node> {

    public HeuristicNodeComparator(final Node node) {
        goal = node;
    }


    private final Node goal;
    private static final double D2 = Math.sqrt(2) - 2;


    @Override
    public int compare(final Node o1, final Node o2) {

//            final double o1Cost = diagonalHeuristic(goal, o1);
//            final double o2Cost = diagonalHeuristic(goal, o2);

        final int o1Cost = manhattanHeuristic(goal, o1);
        final int o2Cost = manhattanHeuristic(goal, o2);
        if (o1Cost == o2Cost) {
            return 0;
        }
        return o1Cost > o2Cost ? 1 : -1;
    }

    private static double diagonalHeuristic(final Node goal, final Node node) {
        final int dx = Math.abs(node.getX() - goal.getX());
        final int dy = Math.abs(node.getY() - goal.getY());
        //   When D1 = 1 and D2 = 1, this is called the Chebyshev distance.
        // When D1 = 1 and D2 = sqrt(2), this is called the octile distance.
        //   return D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy);
//            return dx >= dy ? dx : dy;
        return (dx + dy) + D2 * Math.min(dx, dy);
    }

    private static int manhattanHeuristic(final Node a, final Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

}