package com.smolnij.research.pathfinding;


import com.smolnij.research.pathfinding.heuristic.HeuristicNodeComparator;

import java.util.*;

public class GreedyBestFirstSearch {

    private Collection<Node> closed = new ArrayList<>();

    public List<Node> greedyBestFirstSearch(final Node start, final Node goal, final Node[][] map) {
        closed = new ArrayList<>();
//        final PriorityBuffer open = new PriorityBuffer(new HeuristicNodeComparator(goal));
        final PriorityQueue<Node> open = new PriorityQueue<>(new HeuristicNodeComparator<>(goal));

        //FIXME open.add(new Node()) used here instead of open.add(start), but! but! maybe we can use coords here instead of Node
        //moreover pathfinder getNeighbors and setPathParent has to do ONLY
        //with path finder Node, not MAP NODE
//        open.add(start);
        open.add(new Node(start.getX(), start.getY(), start.isBlocked()));
        while (!open.isEmpty()) {
            final Node current = open.remove();
            if (goal.equals(current)) {
                return constructPath(current);
            }
            closed.add(current);
            for (final Node n : current.getNeighbors(map)) {
                if (!closed.contains(n)) {
                    n.setPathParent(current);
                    open.add(n);
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Node> constructPath(Node node) {
        final List<Node> path = new ArrayList<>();
        while (node.getPathParent() != null) {
            path.add(node);
            node = node.getPathParent();
        }
        return path;
    }

    public Collection<Node> getClosed() {
        return closed;
    }
}

