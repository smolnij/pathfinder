package com.smolnij.research.pathfinding.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.GridCoordinatesAware;

import java.util.Comparator;

public class GreedyNodeComparator<T extends GridCoordinatesAware> implements Comparator<T> {

    private final T goal;
    private final Heuristic<T> heuristic;

    public GreedyNodeComparator(final T node, final Heuristic<T> heuristic) {
        goal = node;
        this.heuristic = heuristic;
    }

    @Override
    public int compare(final T o1, final T o2) {
        return (int) (heuristic.estimate(goal, o1) - heuristic.estimate(goal, o2));
    }
}