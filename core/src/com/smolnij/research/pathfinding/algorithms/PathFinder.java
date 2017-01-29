package com.smolnij.research.pathfinding.algorithms;

import com.smolnij.research.pathfinding.Node;

import java.util.Set;

public abstract class PathFinder {


    public abstract void init(final Node start, final Node goal,
                              final Node[][] graph);

    public abstract boolean update(final Set<PathGraphNode> progress);
}
