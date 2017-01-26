package com.smolnij.research.pathfinding;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BestFirstSearch {


    public List<Node> bestFirstSearch(final Node start, final Node goal, final Node[][] map) {
        final LinkedList<Node> open = new LinkedList<>();
        final LinkedList<Node> closed = new LinkedList<>();

        open.add(start);


        while (!open.isEmpty()) {
            final Node current = open.remove(); //get best
            final double estimate = heuristic(current, goal);
            closed.add(current);
            if (goal.equals(current)) {
                System.out.println("path found");
                return null;
            }
            final List<Node> neighbors = current.getNeighbors(map);
            for (final Node neighbor : neighbors) {
                if (!closed.contains(neighbor)) {
                    open.add(neighbor); // + eval
                    neighbor.setPathParent(current);
                } else {

                }
            }


        }


        return new ArrayList<>();
    }

    private static List<Node> constructPath(Node node) {
        final List<Node> path = new ArrayList<>();
        while (node.getPathParent() != null) {
            path.add(node);
            node = node.getPathParent();
        }
        return path;
    }

    public double heuristic(final Node node, final Node endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }

}
