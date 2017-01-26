package com.smolnij.research.pathfinding.algorithms;


import com.smolnij.research.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;

public class BestFirstSearch2 extends SearchAlgorithms {



    private LinkedList<Node> open = new LinkedList<>();
    private LinkedList<Node> closed = new LinkedList<>();

    public BestFirstSearch2(int refreshRate, Node[][] map) {
        super(refreshRate, map);
    }

    public List<Node> run(final Node start, final Node goal) {
        open.add(start);
        return super.run(start, goal);
    }


    @Override
    public boolean update(final Node goal) {
        Node current = open.get(0);

        double currentEstimate = heuristic(current, goal);
        for (final Node node : open) {
            double newEstimate = heuristic(node, goal);
            if (newEstimate <= currentEstimate) {
                current = node;
                currentEstimate = newEstimate;
            }
        }
        open.remove(current);
        current.setState(NodeState.UNDER_INSPECTION);
        waitMs(100);
        current.setState(NodeState.INSPECTED);
        closed.add(current);
        if (goal.equals(current)) {
            System.out.println("path found");
            found = true;
            return true;
        } else {
            final List<Node> toAdd = current.getNeighbors(map);
            for (final Node newNode : toAdd) {
                if (!closed.contains(newNode)) {
                    newNode.setPathParent(current);
                    newNode.setState(NodeState.CANDIDATE_UNDER_INSPECTION);
                    waitMs(100);
                    newNode.setState(NodeState.INSPECTED_CANDIDATE);
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

    private void waitMs(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double heuristic(final Node node, final Node endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }

}
