package com.smolnij.research.pathfinding.algorithms;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.Node;

import java.util.Set;

public abstract class PathFinder<T extends PathGraphNode> {

    protected T start;
    protected T goal;
    private final Heuristic<T> heuristic;

    protected PathFinder(final Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    public abstract void init(final Node start, final Node goal, final Node[][] graph);

    public abstract boolean update(final Set<PathGraphNode> progress);

    protected void markPath() {
        PathGraphNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            currentNode = currentNode.getParent();
        }
    }

    public Heuristic<T> getHeuristic() {
        return heuristic;
    }
}
