package com.smolnij.research.pathfinding;


public class MapNode implements GridCoordinatesAware {

    private final int x;
    private final int y;
    private final boolean blocked;

    public MapNode(final int x, final int y, final boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "x: " + x + "; y: " + y + ";";
    }

    @Override
    public boolean equals(final Object otherNode) {
        if (this == otherNode) return true;
        if (otherNode == null || getClass() != otherNode.getClass()) return false;

        final MapNode node = (MapNode) otherNode;

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
}
