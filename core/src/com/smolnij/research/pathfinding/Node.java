package com.smolnij.research.pathfinding;


import java.util.ArrayList;
import java.util.List;

public class Node implements TileCoordinatesAware {

    private final int x;
    private final int y;
    private final boolean blocked;


    private Node pathParent;

    public Node(final int x, final int y, final boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    List<Node> getNeighbors(final Node[][] map) {
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

        if (y + 1 < map.length) {
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

    Node getPathParent() {
        return pathParent;
    }

    void setPathParent(final Node pathParent) {
        this.pathParent = pathParent;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        final Node nodeToCompare = (Node) obj;
        return nodeToCompare.x == x && nodeToCompare.y == y;
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

    boolean isBlocked() {
        return blocked;
    }

}
