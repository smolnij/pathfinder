package com.smolnij.research.pathfinding.algorithms;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BestFirstSearch extends SearchAlgorithms {


    private final LinkedList<PathGraphNode> open = new LinkedList<>();
    private final LinkedList<PathGraphNode> closed = new LinkedList<>();

    public BestFirstSearch(final int refreshRate, final PathGraphNode[][] map) {
        super(refreshRate, map);
        //todo priority queue
    }

    public List<PathGraphNode> run(final PathGraphNode start, final PathGraphNode goal) {
        open.add(start);
        return super.run(start, goal);
    }

    public void init(final PathGraphNode start, final PathGraphNode goal) {
        open.add(start);
        super.goal = goal;
    }


    @Override
    public boolean update(final Set<PathGraphNode> nodesToVisualize) {
        PathGraphNode current = open.get(0);
        final PathGraphNode goal = super.goal;

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
            System.out.println("path found");
            found = true;
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
        if (open.isEmpty()) {
            found = false;
            return true;
        }
        return false;
    }

    private double heuristic(final PathGraphNode node, final PathGraphNode endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }

}
