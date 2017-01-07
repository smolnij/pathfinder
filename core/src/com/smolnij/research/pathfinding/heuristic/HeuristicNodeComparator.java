package com.smolnij.research.pathfinding.heuristic;

import com.smolnij.research.pathfinding.Node;

import java.util.Comparator;

public class HeuristicNodeComparator <T>/*<T extends TileCoordinatesAware> implements Comparator<T> */
implements Comparator<T> {

    public HeuristicNodeComparator(final Node node) {

    }

    @Override
    public int compare(final T o1, final T o2) {
        return 0;
    }

   /* private final TileCoordinatesAware goal;
    private static final double D2 = Math.sqrt(2) - 2;

    HeuristicNodeComparator(final TileCoordinatesAware goal) {
        this.goal = goal;
    }

    @Override
    public int compare(final TileCoordinatesAware o1, final TileCoordinatesAware o2) {

//            final double o1Cost = diagonalHeuristic(goal, o1);
//            final double o2Cost = diagonalHeuristic(goal, o2);

        final int o1Cost = manhattanHeuristic(goal, o1);
        final int o2Cost = manhattanHeuristic(goal, o2);
        if (o1Cost == o2Cost) {
            return 0;
        }
        return o1Cost > o2Cost ? 1 : -1;
    }

    private static double diagonalHeuristic(final TileCoordinatesAware goal, final TileCoordinatesAware node) {
        final int dx = Math.abs(node.getX() - goal.getX());
        final int dy = Math.abs(node.getY() - goal.getY());
        //   When D1 = 1 and D2 = 1, this is called the Chebyshev distance.
        // When D1 = 1 and D2 = sqrt(2), this is called the octile distance.
        //   return D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy);
//            return dx >= dy ? dx : dy;
        return (dx + dy) + D2 * Math.min(dx, dy);
    }

    private static int manhattanHeuristic(final TileCoordinatesAware a, final TileCoordinatesAware b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }*/
}