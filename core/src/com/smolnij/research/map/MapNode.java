package com.smolnij.research.map;


public class MapNode extends TiledMapPoint {

    private final boolean blocked;

    public MapNode(final int x, final int y, final boolean blocked) {
        super(x, y);
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

    public boolean isBlocked() {
        return blocked;
    }
}
