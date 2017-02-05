package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.datastructure.WaypointsGraph;
import com.smolnij.research.pathfinding.graph.NodeState;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;
import com.smolnij.research.pathfinding.heuristic.AStarNodeComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarPathFinder extends PathFinder<StatefulGraphMapNode> {

    public static final String NAME = "A*";

    private final List<StatefulGraphMapNode> closed = new ArrayList<>();
    private PriorityQueue<StatefulGraphMapNode> open;
    private WaypointsGraph waypointsGraph;

    public AStarPathFinder(final Heuristic<StatefulGraphMapNode> heuristic) {
        super(heuristic);
    }

    @Override
    public void init(final MapNode start, final MapNode goal, final MapNode[][] maze) {
        closed.clear();
        this.waypointsGraph = new WaypointsGraph(maze);


        this.goal = waypointsGraph.get(goal.getX(), goal.getY());
        final StatefulGraphMapNode startGraphNode = waypointsGraph.get(start.getX(), start.getY());
        this.start = startGraphNode;

        open = new PriorityQueue<>(new AStarNodeComparator<>(start, goal));

        open.add(startGraphNode);
    }

    @Override
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
}
