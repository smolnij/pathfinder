package com.smolnij.research.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

public class IndexedAStarDrawAwarePathFinder<T> extends IndexedAStarPathFinder<T> {

    public IndexedAStarDrawAwarePathFinder(final IndexedGraph<T> graph) {
        super(graph);
    }

    public IndexedAStarDrawAwarePathFinder(final IndexedGraph<T> graph, final boolean calculateMetrics) {
        super(graph, calculateMetrics);
    }

}
