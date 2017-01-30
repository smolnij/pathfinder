package com.smolnij.research.pathfinding.algorithms;

import com.smolnij.research.pathfinding.Node;

import java.util.Set;

public abstract class PathFinder<T extends PathGraphNode> {

    protected T start;
    protected T goal;

    public abstract void init(final Node start, final Node goal, final Node[][] graph);

    public abstract boolean update(final Set<PathGraphNode> progress);

    protected void markPath() {
        PathGraphNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            currentNode = currentNode.getParent();
        }
    }
}
