package com.smolnij.research.pathfinding.heuristic;

import com.smolnij.research.pathfinding.GridCoordinatesAware;

import java.util.Comparator;

public class AStarNodeComparator<T extends GridCoordinatesAware>
        implements Comparator<T> {

    private final T start;
    private final T goal;

    public AStarNodeComparator(final T start, final T goal) {
        this.goal = goal;
        this.start = start;
    }


    private static final double D2 = Math.sqrt(2) - 2;


    @Override
    public int compare(final T o1, final T o2) {

//            final double o1Cost = diagonalHeuristic(goal, o1);
//            final double o2Cost = diagonalHeuristic(goal, o2);

        final int o1Cost = manhattanHeuristic(goal, o1) + manhattanHeuristic(start, o1);
        final int o2Cost = manhattanHeuristic(goal, o2) + manhattanHeuristic(start, o2);
        return o1Cost - o2Cost;
    }

    private double diagonalHeuristic(final T goal, final T node) {
        final int dx = Math.abs(node.getX() - goal.getX());
        final int dy = Math.abs(node.getY() - goal.getY());
        //   When D1 = 1 and D2 = 1, this is called the Chebyshev distance.
        // When D1 = 1 and D2 = sqrt(2), this is called the octile distance.
        //   return D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy);
//            return dx >= dy ? dx : dy;
        return (dx + dy) + D2 * Math.min(dx, dy);
    }

    private int manhattanHeuristic(final T a, final T b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

}