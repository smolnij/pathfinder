package com.smolnij.research.pathfinding.graph;

import com.smolnij.research.pathfinding.MapNode;

public class StatefulGraphMapNode extends MapNode {
    private NodeState state;
    private StatefulGraphMapNode parent;

    public StatefulGraphMapNode(int x, int y, boolean blocked) {
        super(x, y, blocked);
    }

    public StatefulGraphMapNode getParent() {
        return parent;
    }

    public void setParent(final StatefulGraphMapNode parent) {
        this.parent = parent;
    }


    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }
}
