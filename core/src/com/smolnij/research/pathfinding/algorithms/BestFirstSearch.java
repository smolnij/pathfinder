package com.smolnij.research.pathfinding.algorithms;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BestFirstSearch {


    private final LinkedList<PathGraphNode> open = new LinkedList<>();
    private final LinkedList<PathGraphNode> closed = new LinkedList<>();
    private PathGraphNode start;
    private PathGraphNode goal;
    private PathGraphNode[][] map;

    public BestFirstSearch() {
        //todo priority queue
    }


    public void init(final PathGraphNode start, final PathGraphNode goal, final PathGraphNode[][] map) {
        open.add(start);
        this.goal = goal;
        this.start = start;
        this.map = map;
    }


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
            markPath(start, goal);
            return true;
        } else {
            final List<PathGraphNode> toAdd = current.getNeighbors(map);
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

    private double heuristic(final PathGraphNode node, final PathGraphNode endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }

    private void markPath(final PathGraphNode start, final PathGraphNode goal) {
        PathGraphNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            currentNode = currentNode.getParent();
        }
    }
}
