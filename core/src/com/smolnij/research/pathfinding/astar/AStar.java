package com.smolnij.research.pathfinding.astar;

import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.algorithms.NodeState;
import com.smolnij.research.pathfinding.algorithms.PathFinder;
import com.smolnij.research.pathfinding.algorithms.PathGraphNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AStar extends PathFinder {

    private AStarNode[][] mazeGraph;
    private AStarNode start;
    private AStarNode goal;
    private final List<AStarNode> open = new LinkedList<>();
    private final List<AStarNode> closed = new LinkedList<>();


    public void init(final Node start, final Node goal, final Node[][] maze) {
        open.clear();
        closed.clear();
        this.mazeGraph = buildGraph(maze);

        final AStarNode startGraphNode = mazeGraph[start.getX()][start.getY()];
        open.add(startGraphNode);
        this.goal = mazeGraph[goal.getX()][goal.getY()];
        this.start = startGraphNode;

    }

    private AStarNode[][] buildGraph(final Node[][] maze) {
        final AStarNode[][] graph = new AStarNode[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                final Node node = maze[i][j];
                graph[i][j] = new AStarNode(node.getX(), node.getY(), node.isBlocked());
            }
        }
        return graph;
    }

    private void markPath() {
        PathGraphNode pathPoint = goal.getParent();
        while (!pathPoint.equals(start)) {
            pathPoint.setState(NodeState.PATH);
            pathPoint = pathPoint.getParent();
            if (pathPoint == null) break;
        }
    }

    public boolean update(final Set<PathGraphNode> nodesToVisualize) {
        final AStarNode currentNode = findCheapest(open);
        open.remove(currentNode);
        closed.add(currentNode);
        currentNode.setState(NodeState.INSPECTED);
        nodesToVisualize.add(currentNode);

        final List<AStarNode> neighbors = getNeighbors(currentNode, mazeGraph);
        for (final AStarNode neighbor : neighbors) {
            if (closed.contains(neighbor)) {
                continue;
            }

            if (!open.contains(neighbor)) {
                neighbor.setParent(currentNode);
                neighbor.h = heuristic(neighbor, goal);
                neighbor.g = calcStepPrice(currentNode, start);
                neighbor.f = neighbor.h + neighbor.g;
                open.add(neighbor);
                nodesToVisualize.add(currentNode);
                currentNode.setState(NodeState.INSPECTED_CANDIDATE);
                continue;
            }

            if (neighbor.g + calcStepPrice(currentNode, neighbor) < currentNode.g) {
                neighbor.setParent(currentNode);
                neighbor.h = heuristic(neighbor, goal);
                neighbor.g = calcStepPrice(start, currentNode);
                neighbor.f = neighbor.h + neighbor.g;
            }
        }

        if (open.contains(goal)) {
            markPath();
            return true;
        }

        return open.isEmpty();
    }


    private int calcStepPrice(final AStarNode a, final AStarNode b) {
        if (a.getX() == b.getX() || a.getY() == b.getY()) {
            return 10;
        } else {
            return 14;
        }
    }

    private int heuristic(final AStarNode a, final AStarNode b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private List<AStarNode> getNeighbors(final AStarNode node, final AStarNode[][] map) {
        final int x = node.getX();
        final int y = node.getY();
        final List<AStarNode> neighbors = new ArrayList<>(8);


        if (y - 1 >= 0) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y - 1].isBlocked()) {
                    neighbors.add(map[x - 1][y - 1]);
                }
            }
            if (!map[x][y - 1].isBlocked()) {
                neighbors.add(map[x][y - 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y - 1].isBlocked()) {
                    neighbors.add(map[x + 1][y - 1]);
                }
            }
        }
        if (x - 1 >= 0) {
            if (!map[x - 1][y].isBlocked()) {
                neighbors.add(map[x - 1][y]);
            }
        }
        if (x + 1 < map.length) {
            if (!map[x + 1][y].isBlocked()) {
                neighbors.add(map[x + 1][y]);
            }
        }

        if (y + 1 < map[0].length) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y + 1].isBlocked()) {
                    neighbors.add(map[x - 1][y + 1]);
                }
            }
            if (!map[x][y + 1].isBlocked()) {
                neighbors.add(map[x][y + 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y + 1].isBlocked()) {
                    neighbors.add(map[x + 1][y + 1]);
                }
            }
        }

        return neighbors;
    }

    private AStarNode findCheapest(final List<AStarNode> list) {
        AStarNode cheapest = list.get(0);
        for (final AStarNode node : list) {
            if (node.f < cheapest.f) {
                cheapest = node;
            }
        }
        return cheapest;
    }
}
