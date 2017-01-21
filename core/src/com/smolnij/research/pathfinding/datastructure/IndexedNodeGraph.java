package com.smolnij.research.pathfinding.datastructure;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.smolnij.research.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class IndexedNodeGraph implements IndexedGraph<Node> {

    private final Node[][] map;

    public IndexedNodeGraph(final Node[][] map) {
        this.map = map;
    }

    @Override
    public int getIndex(final Node node) {
//        final int i = node.getX() * map.length + node.getY();
        final int i = node.getY() * map.length + node.getX();

        System.out.println("node index: " + i);
        return i;
    }

    @Override
    public int getNodeCount() {
        final int i = map.length * map[0].length;
        System.out.println("Node count: " + i);
        return i;
    }

    @Override
    public Array<Connection<Node>> getConnections(final Node fromNode) {
        final Array<Connection<Node>> array = new Array<>();

        final List<Node> neighbors = getNeighbors(fromNode);
        for (final Node neighbor : neighbors) {// fixme lambda
            array.add(new DefaultConnection<>(fromNode, neighbor));
        }

//        getNeighbors(fromNode).stream().map(node -> new DefaultConnection<>(fromNode, node)).forEach(array::add);

        return array;
    }


    public List<Node> getNeighbors(final Node node) {
        final int x = node.getX();
        final int y = node.getY();
        final List<Node> neighbors = new ArrayList<>(8);


        if (y - 1 >= 0) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y - 1].isBlocked()) {
                    neighbors.add(map[x - 1][y - 1]);
                }
            }
            if (!map[x][y - 1].isBlocked()) {
                neighbors.add(map[x][y - 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y - 1].isBlocked()) {
                    neighbors.add(map[x + 1][y - 1]);
                }
            }
        }
        if (x - 1 >= 0) {
            if (!map[x - 1][y].isBlocked()) {
                neighbors.add(map[x - 1][y]);
            }
        }
        if (x + 1 < map.length) {
            if (!map[x + 1][y].isBlocked()) {
                neighbors.add(map[x + 1][y]);
            }
        }

        if (y + 1 < map[0].length) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y + 1].isBlocked()) {
                    neighbors.add(map[x - 1][y + 1]);
                }
            }
            if (!map[x][y + 1].isBlocked()) {
                neighbors.add(map[x][y + 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y + 1].isBlocked()) {
                    neighbors.add(map[x + 1][y + 1]);
                }
            }
        }

        System.out.println("Neighbors of " + x + ", " + y + " are: ");
        for (final Node neighbor : neighbors) {
            System.out.print("( " + neighbor.getX() + ", " + neighbor.getY() + "); ");
        }
        System.out.println();
        return neighbors;
    }
}
