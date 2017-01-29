package com.smolnij.research.pathfinding.algorithms;


import com.smolnij.research.pathfinding.GridCoordinatesAware;
import com.smolnij.research.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BestFirstPathFinder extends PathFinder {


    private final LinkedList<PathGraphNode> open = new LinkedList<>();
    private final LinkedList<PathGraphNode> closed = new LinkedList<>();

    private PathGraphNode[][] mazeGraph;

    public BestFirstPathFinder() {
        //todo priority queue
    }


    @Override
    public void init(final Node start, final Node goal, final Node[][] maze) {
        open.clear();
        closed.clear();
        this.mazeGraph = buildGraph(maze);

        final PathGraphNode startGraphNode = mazeGraph[start.getX()][start.getY()];
        open.add(startGraphNode);
        this.goal = mazeGraph[goal.getX()][goal.getY()];
        this.start = startGraphNode;

    }

    private PathGraphNode[][] buildGraph(final Node[][] maze) {
        final PathGraphNode[][] graph = new PathGraphNode[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                graph[i][j] = new PathGraphNode(i, j, maze[i][j].isBlocked());
            }
        }
        return graph;
    }

    @Override
    public boolean update(final Set<PathGraphNode> nodesToVisualize) {
        PathGraphNode current = open.get(0);

        double currentEstimate = heuristic(current, goal);
        for (final PathGraphNode node : open) {
            double newEstimate = heuristic(node, goal);
            if (newEstimate <= currentEstimate) {
                current = node;
                currentEstimate = newEstimate;
            }
        }
        open.remove(current);
        current.setState(NodeState.INSPECTED);
        nodesToVisualize.add(current);
        closed.add(current);
        if (goal.equals(current)) {
            markPath();
            return true;
        } else {
            final List<PathGraphNode> toAdd = current.getNeighbors(mazeGraph);
            for (final PathGraphNode newNode : toAdd) {
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

    private double heuristic(final GridCoordinatesAware node, final GridCoordinatesAware endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }
}
