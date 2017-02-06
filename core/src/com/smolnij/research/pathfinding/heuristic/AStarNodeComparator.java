package com.smolnij.research.pathfinding.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.GridCoordinatesAware;

import java.util.Comparator;

public class AStarNodeComparator<T extends GridCoordinatesAware> implements Comparator<T> {

    private final T start;
    private final T goal;
    private final Heuristic<T> heuristic;


    public AStarNodeComparator(final T start, final T goal, final Heuristic<T> heuristic) {
        this.goal = goal;
        this.start = start;
        this.heuristic = heuristic;
    }

    @Override
    public int compare(final T o1, final T o2) {

        final float o1Cost = heuristic.estimate(goal, o1) + heuristic.estimate(start, o1);
        final float o2Cost = heuristic.estimate(goal, o2) + heuristic.estimate(start, o2);
        return (int) (o1Cost - o2Cost);
    }
}