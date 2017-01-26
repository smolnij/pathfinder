package com.smolnij.research.scene;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.GreedyBestFirstSearch;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.algorithms.BestFirstSearch2;
import com.smolnij.research.pathfinding.datastructure.IndexedNodeGraph;
import com.smolnij.research.pathfinding.heuristic.ManhattanDistance;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    private List<Node> path = new ArrayList<>();
    private final Node[][] maze;
    private final Node start;
    private final Node end;
    private boolean ready;
    private GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch();

    public PathFinder(final Node[][] maze, final TiledMapPoint start, final TiledMapPoint end) {
        this.maze = maze;
        this.start = maze[start.x][start.y];
        this.end = maze[end.x][end.y];
    }

    public void findPath() {
//        final DefaultGraphPath<Node> result = indexedAStarPath();
//        path.addAll(Arrays.asList(result.nodes.toArray()));
        path.clear();

        new Thread(() -> {
            System.out.println("Findpath thread started");
//            final List<Node> nodes = greedyBestFirstSearch.greedyBestFirstSearch(start, end, maze);

            final BestFirstSearch2 bfs2 = new BestFirstSearch2(50, maze);
            final List<Node> nodes = bfs2.run(start, end);


//            final List<Node> nodes = new AStar(maze).search(start, end);
            path.addAll(nodes);
            System.out.println("Search ended, nodes found: " + nodes.size());
            ready = true;
        }).start();
    }

    /*public void threadLibgdx() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                final Result result = createResult();
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        results.add(result);
                    }
                });
            }
        }).start();
    }*/


    private DefaultGraphPath<Node> indexedAStarPath() {
        final DefaultGraphPath<Node> result = new DefaultGraphPath<>();
        final IndexedAStarPathFinder<Node> pf = new IndexedAStarPathFinder<>(new IndexedNodeGraph(maze));
        final Heuristic<Node> heuristic = new ManhattanDistance<>();

        final boolean b = pf.searchNodePath(start, end, heuristic, result);
        System.out.println("Path found: " + b);
        return result;
    }

    public void render(final SpriteBatch batch) {
        batch.begin();
        if (ready) {

            for (final Node node : path) {
                batch.draw(AtlasHelper.INSTANCE.findRegion("path"), node.getX(), node.getY(), 1, 1);
            }


        }
        batch.end();
    }

    public void clearPaths() {
        path.clear();
    }
}
