package com.smolnij.research.pathfinding.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class SearchAlgorithms {

    protected boolean found = false;
    protected final int refreshRate;
    protected final PathGraphNode[][] map;
    protected PathGraphNode goal;

    public SearchAlgorithms(final int refreshRate, final PathGraphNode[][] map) {
        this.refreshRate = refreshRate;
        this.map = map;
    }

    public List<PathGraphNode> run(final PathGraphNode start, final PathGraphNode goal) {
        this.goal = goal;
        search();
        //If the target is reached, draw the path
        if (found) {
            return findPath(start, goal);
        }
        return new LinkedList<>();
    }

    private List<PathGraphNode> findPath(final PathGraphNode start, final PathGraphNode goal) {
        final List<PathGraphNode> path = new LinkedList<>();

        PathGraphNode currentNode = goal;
        while (!currentNode.equals(start)) {
            currentNode.setState(NodeState.PATH);
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        return path;

        /*for (final Node node : path){
            node.State = 4;
            _map.Draw(_root, _target, _title);
        }*/
    }

    private void search() {
        boolean finished = false;
        while (!finished) {
            for (int i = 0; !finished && i < refreshRate; i++) {
                finished = update(new HashSet<>());
            }
//            _map.Draw(_root, _target, _title);
        }
    }

    abstract boolean update(Set<PathGraphNode> nodes);
}
