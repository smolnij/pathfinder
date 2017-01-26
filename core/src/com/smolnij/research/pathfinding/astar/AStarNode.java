package com.smolnij.research.pathfinding.astar;

import com.smolnij.research.pathfinding.Node;

public class AStarNode extends Node {

    public int f = 0;
    public int g = 0;
    public int h = 0;
    public AStarNode parent;

    public AStarNode(int x, int y, boolean blocked) {
        super(x, y, blocked);
    }



}
