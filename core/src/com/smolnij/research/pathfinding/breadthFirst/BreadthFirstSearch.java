package com.smolnij.research.pathfinding.breadthFirst;

import com.smolnij.research.pathfinding.Node;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BreadthFirstSearch {


    public static List<Node> search(Node startNode, Node goalNode, final Node[][] map) {
        // list of visited nodes
        LinkedList<Node> closedList = new LinkedList<>();

        // list of nodes to visit (sorted)
        LinkedList<Node> openList = new LinkedList<>();
        openList.add(startNode);
        startNode.setPathParent(null);

        while (!openList.isEmpty()) {
            Node node = openList.removeFirst();
            if (node.equals(goalNode)) {
                // path found!
                return constructPath(goalNode);
            } else {
                closedList.add(node);

                // add neighbors to the open list
                final List<Node> neighbors = node.getNeighbors(map);
                for (Node neighborNode : neighbors) {
                    if (!closedList.contains(neighborNode) &&
                            !openList.contains(neighborNode)) {
                        neighborNode.setPathParent(node);
                        openList.add(neighborNode);
                    }
                }
            }
        }

        // no path found
        System.out.println("No path");
        return Collections.emptyList();
    }

    private static List<Node> constructPath(Node node) {
        System.out.println("Path found, constuction");
        LinkedList<Node> path = new LinkedList<>();
        while (node.getPathParent() != null) {
            path.addFirst(node);
            node = node.getPathParent();
        }
        return path;
    }
}