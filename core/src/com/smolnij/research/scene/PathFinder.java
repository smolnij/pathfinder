package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.GreedyBestFirstSearch;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.astar.AStar;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    private List<Node> path = new ArrayList<>();

    private final Node start;
    private final Node end;
    private boolean ready;
    private GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch();
    private final MazeRenderer mazeRenderer;

    public PathFinder(final MazeRenderer mazeRenderer, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath() {
        path.clear();
//        final DefaultGraphPath<Node> result = indexedAStarPath();
//        path.addAll(Arrays.asList(result.nodes.toArray()));


        new Thread(() -> {
            System.out.println("Findpath thread started");
//            final List<Node> nodes = greedyBestFirstSearch.greedyBestFirstSearch(start, end, maze);

//            final BestFirstSearch bfs2 = new BestFirstSearch(50, mazeRenderer.getMaze());
//            final List<Node> nodes = bfs2.run(start, end);

//            final List<Node> nodes = BreadthFirstSearch.search(start, end, mazeRenderer.getMaze());

            final List<Node> nodes = new AStar(mazeRenderer.getMaze()).search(start, end);
            path.addAll(nodes);
            System.out.println("Search ended, nodes found: " + nodes.size());
            ready = true;
        }).start();
        System.out.println("done");
        ready = true;
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
