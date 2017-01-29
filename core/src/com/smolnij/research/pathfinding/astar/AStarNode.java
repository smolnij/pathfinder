package com.smolnij.research.pathfinding.astar;

import com.smolnij.research.pathfinding.algorithms.PathGraphNode;

public class AStarNode extends PathGraphNode {

    public int f = 0;
    public int g = 0;
    public int h = 0;

    public AStarNode(int x, int y, boolean blocked) {
        super(x, y, blocked);
    }


}
