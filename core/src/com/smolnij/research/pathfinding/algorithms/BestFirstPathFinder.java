package com.smolnij.research.pathfinding.algorithms;


import com.badlogic.gdx.ai.pfa.Heuristic;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.heuristic.GreedyNodeComparator;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFirstPathFinder extends PathFinder<PathGraphNode> {
    private final LinkedList<PathGraphNode> closed = new LinkedList<>();
    private PriorityQueue<PathGraphNode> open;
    private PathGraphNode[][] mazeGraph;

    public BestFirstPathFinder(final Heuristic<PathGraphNode> heuristic) {
        super(heuristic);
    }

    @Override
    public void init(final Node start, final Node goal, final Node[][] maze) {
        closed.clear();
        this.mazeGraph = buildGraph(maze);
        final PathGraphNode startGraphNode = mazeGraph[start.getX()][start.getY()];
        this.goal = mazeGraph[goal.getX()][goal.getY()];
        this.start = startGraphNode;


        open = new PriorityQueue<>(new GreedyNodeComparator<>(this.goal, getHeuristic()));
        open.add(startGraphNode);
    }

    private PathGraphNode[][] buildGraph(final Node[][] maze) {
        final PathGraphNode[][] graph = new PathGraphNode[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                graph[i][j] = new PathGraphNode(i, j, maze[i][j].isBlocked());
            }
        }
        return graph;
    }

    @Override
    public boolean update(final Set<PathGraphNode> nodesToVisualize) {
        final PathGraphNode current = open.poll();
        current.setState(NodeState.INSPECTED);
        nodesToVisualize.add(current);
        closed.add(current);
        if (goal.equals(current)) {
            markPath();
            return true;
        } else {
            final List<PathGraphNode> toAdd = current.getNeighbors(mazeGraph);
            for (final PathGraphNode newNode : toAdd) {
                if (!closed.contains(newNode)) {
                    newNode.setParent(current);
                    newNode.setState(NodeState.INSPECTED_CANDIDATE);
                    nodesToVisualize.add(newNode);
                    open.add(newNode);
                    closed.add(newNode);
                }

            }
        }
        return open.isEmpty();
    }
}
