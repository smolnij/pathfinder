package com.smolnij.research.pathfinding;


import com.smolnij.research.pathfinding.algorithms.NodeState;

import java.util.ArrayList;
import java.util.List;

public class Node implements TileCoordinatesAware {

    private final int x;
    private final int y;
    private final boolean blocked;
    private NodeState state;


    private Node pathParent;

    public Node(final int x, final int y, final boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    public List<Node> getNeighbors(final Node[][] map) {
        final List<Node> neighbors = new ArrayList<>(8);
        if (y - 1 >= 0) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y - 1].blocked) {
                    neighbors.add(map[x - 1][y - 1]);
                }
            }
            if (!map[x][y - 1].blocked) {
                neighbors.add(map[x][y - 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y - 1].blocked) {
                    neighbors.add(map[x + 1][y - 1]);
                }
            }
        }
        if (x - 1 >= 0) {
            if (!map[x - 1][y].blocked) {
                neighbors.add(map[x - 1][y]);
            }
        }
        if (x + 1 < map.length) {
            if (!map[x + 1][y].blocked) {
                neighbors.add(map[x + 1][y]);
            }
        }

        if (y + 1 < map[0].length) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y + 1].blocked) {
                    neighbors.add(map[x - 1][y + 1]);
                }
            }
            if (!map[x][y + 1].blocked) {
                neighbors.add(map[x][y + 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y + 1].blocked) {
                    neighbors.add(map[x + 1][y + 1]);
                }
            }
        }

        return neighbors;
    }

    public Node getPathParent() {
        return pathParent;
    }

    public void setPathParent(final Node pathParent) {
        this.pathParent = pathParent;
    }

    @Override
    public String toString() {
        return "x: " + x + "; y: " + y + ";";
    }

    @Override
    public boolean equals(final Object otherNode) {
        if (this == otherNode) return true;
        if (otherNode == null || getClass() != otherNode.getClass()) return false;

        final Node node = (Node) otherNode;

        return x == node.x && y == node.y && blocked == node.blocked;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(final NodeState state) {
        this.state = state;
    }
}
