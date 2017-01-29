package com.smolnij.research.pathfinding.algorithms;

import com.smolnij.research.pathfinding.GridCoordinatesAware;

import java.util.ArrayList;
import java.util.List;

public class PathGraphNode implements GridCoordinatesAware {
    private int x;
    private int y;
    private NodeState state;
    private boolean blocked;

    public PathGraphNode(int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    public PathGraphNode getParent() {
        return parent;
    }

    public void setParent(final PathGraphNode parent) {
        this.parent = parent;
    }

    private PathGraphNode parent;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public boolean isBlocked() {
        return blocked;
    }

    //todo that is up to list to return neighbor, not node itself
    public List<PathGraphNode> getNeighbors(final PathGraphNode[][] map) {
        final List<PathGraphNode> neighbors = new ArrayList<>(8);
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
}
