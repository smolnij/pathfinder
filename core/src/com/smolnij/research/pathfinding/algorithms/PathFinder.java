package com.smolnij.research.pathfinding.algorithms;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.NodeState;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

import java.util.Set;

public abstract class PathFinder<T extends StatefulGraphMapNode> {

    protected T start;
    protected T goal;
    private final Heuristic<T> heuristic;

    protected PathFinder(final Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    public abstract void init(final MapNode start, final MapNode goal, final MapNode[][] graph);

    public abstract boolean update(final Set<StatefulGraphMapNode> progress);

    protected void markPath() {
        StatefulGraphMapNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            currentNode = currentNode.getParent();
        }
    }

    public Heuristic<T> getHeuristic() {
        return heuristic;
    }
}
