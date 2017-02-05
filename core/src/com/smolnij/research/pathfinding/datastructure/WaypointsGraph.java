package com.smolnij.research.pathfinding.datastructure;

import com.smolnij.research.map.MapNode;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

import java.util.ArrayList;
import java.util.List;

public class WaypointsGraph {


    private final StatefulGraphMapNode[][] graph;

    public WaypointsGraph(final MapNode[][] maze) {
        graph = buildGraph(maze);
    }

    private StatefulGraphMapNode[][] buildGraph(final MapNode[][] maze) {
        final StatefulGraphMapNode[][] graph = new StatefulGraphMapNode[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                graph[i][j] = new StatefulGraphMapNode(i, j, maze[i][j].isBlocked());
            }
        }
        return graph;
    }

    public StatefulGraphMapNode get(final int x, final int y) {
        return graph[x][y];
    }

    public List<StatefulGraphMapNode> getNeighbors(final MapNode node) {

        final List<StatefulGraphMapNode> neighbors = new ArrayList<>(8);
        final int x = node.getX();
        final int y = node.getY();

        if (y - 1 >= 0) {
            if (x - 1 >= 0) {
                addIfNotBlocked(neighbors, graph[x - 1][y - 1]);
            }
            addIfNotBlocked(neighbors, graph[x][y - 1]);
            if (x + 1 < graph.length) {
                addIfNotBlocked(neighbors, graph[x + 1][y - 1]);
            }
        }
        if (x - 1 >= 0) {
            addIfNotBlocked(neighbors, graph[x - 1][y]);
        }
        if (x + 1 < graph.length) {
            addIfNotBlocked(neighbors, graph[x + 1][y]);
        }

        if (y + 1 < graph[0].length) {
            if (x - 1 >= 0) {
                addIfNotBlocked(neighbors, graph[x - 1][y + 1]);
            }
            addIfNotBlocked(neighbors, graph[x][y + 1]);
            if (x + 1 < graph.length) {
                addIfNotBlocked(neighbors, graph[x + 1][y + 1]);
            }
        }

        return neighbors;
    }

    private void addIfNotBlocked(final List<StatefulGraphMapNode> neighbors, final StatefulGraphMapNode statefulGraphNode) {
        if (!statefulGraphNode.isBlocked()) {
            neighbors.add(statefulGraphNode);
        }
    }
}
