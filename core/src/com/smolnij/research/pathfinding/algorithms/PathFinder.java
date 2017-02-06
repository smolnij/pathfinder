package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.datastructure.WaypointsGraph;
import com.smolnij.research.pathfinding.graph.NodeState;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public abstract class PathFinder {

    private final List<StatefulGraphMapNode> closed = new ArrayList<>();
    private final Heuristic<StatefulGraphMapNode> heuristic;
    protected StatefulGraphMapNode start;
    protected StatefulGraphMapNode goal;
    private Queue<StatefulGraphMapNode> open;
    private WaypointsGraph waypointsGraph;

    public PathFinder(final Heuristic<StatefulGraphMapNode> heuristic) {
        this.heuristic = heuristic;
    }

    public void init(final MapNode start, final MapNode goal, final MapNode[][] maze) {
        closed.clear();
        this.waypointsGraph = new WaypointsGraph(maze);


        this.goal = waypointsGraph.get(goal.getX(), goal.getY());
        final StatefulGraphMapNode startGraphNode = waypointsGraph.get(start.getX(), start.getY());
        this.start = startGraphNode;

        open = getToBeEvaluatedQueueImplementation(start, goal);

        open.add(startGraphNode);
    }

    abstract Queue<StatefulGraphMapNode> getToBeEvaluatedQueueImplementation(MapNode start, MapNode goal);

    public boolean update(final Set<StatefulGraphMapNode> nodesToVisualize) {
        final StatefulGraphMapNode current = open.poll();
        current.setState(NodeState.INSPECTED);
        nodesToVisualize.add(current);
        closed.add(current);
        if (goal.equals(current)) {
            markPath();
            return true;
        } else {
            final List<StatefulGraphMapNode> toAdd = waypointsGraph.getNeighbors(current);
            for (final StatefulGraphMapNode newNode : toAdd) {
                if (!closed.contains(newNode)) {
                    newNode.setParent(current);
                    newNode.setState(NodeState.INSPECTED_CANDIDATE);
                    nodesToVisualize.add(newNode);
                    open.add(newNode);
                    closed.add(newNode);
                }

            }
        }
        return open.isEmpty();
    }

    private void markPath() {
        StatefulGraphMapNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            currentNode = currentNode.getParent();
        }
    }

    protected Heuristic<StatefulGraphMapNode> getHeuristic() {
        return heuristic;
    }
}
