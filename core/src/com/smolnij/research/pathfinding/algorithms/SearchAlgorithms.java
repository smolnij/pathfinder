package com.smolnij.research.pathfinding.algorithms;

import com.smolnij.research.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;

public abstract class SearchAlgorithms {

    protected boolean found = false;
    protected final int refreshRate;
    protected final Node[][] map;

    public SearchAlgorithms(final int refreshRate, final Node[][] map) {
        this.refreshRate = refreshRate;
        this.map = map;
    }

    public List<Node> run(final Node start, final Node goal) {
        search(goal);
        //If the target is reached, draw the path
        if (found) {
            return findPath(start, goal);
        }
        return new LinkedList<>();
    }

    private List<Node> findPath(final Node start, final Node goal) {
        final List<Node> path = new LinkedList<>();

        Node currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            path.add(currentNode);
            currentNode = currentNode.getPathParent();
        }
        return path;

        /*for (final Node node : path){
            node.State = 4;
            _map.Draw(_root, _target, _title);
        }*/
    }

    private void search(final Node goal) {
        boolean finished = false;
        while (!finished) {
            for (int i = 0; !finished /*&& i < refreshRate*/; i++) {
                finished = update(goal);
            }
//            _map.Draw(_root, _target, _title);
        }
    }

    abstract boolean update(final Node goal);
}
