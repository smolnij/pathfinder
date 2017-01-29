package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.algorithms.BestFirstSearch;
import com.smolnij.research.pathfinding.algorithms.PathGraphNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathFinder {
    public static final int PATH_RENDERING_REFRESH_RATE = 1;
    private List<PathGraphNode> path = new ArrayList<>();
    private Set<PathGraphNode> progress = new HashSet<>();

    private final Node start;
    private final Node end;
    private boolean pathFindingStarted = false;
    private final MazeRenderer mazeRenderer;
    private BestFirstSearch bfs2;

    public PathFinder(final MazeRenderer mazeRenderer, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath() {
        path.clear();
//        new Thread(() -> {
        System.out.println("Findpath thread started");
//            final List<Node> nodes = greedyBestFirstSearch.greedyBestFirstSearch(start, end, maze);

        final PathGraphNode[][] graph = new PathGraphNode[mazeRenderer.getMaze().length][mazeRenderer.getMaze()[0].length];
        for (int i = 0; i < mazeRenderer.getMaze().length; i++) {
            for (int j = 0; j < mazeRenderer.getMaze()[0].length; j++) {
                graph[i][j] = new PathGraphNode(i, j, mazeRenderer.getMaze()[i][j].isBlocked());
            }
        }


        bfs2 = new BestFirstSearch(50, graph);
        bfs2.init(graph[start.getX()][start.getY()], graph[end.getX()][end.getY()]);
//        final List<PathGraphNode> nodes = bfs2.run(graph[start.getX()][start.getY()], graph[end.getX()][end.getY()]);
        pathFindingStarted = true;

//            final List<Node> nodes = BreadthFirstSearch.search(start, end, mazeRenderer.getMaze());

//            final List<Node> nodes = new AStar(mazeRenderer.getMaze()).search(start, end);

//        path.addAll(nodes);
//        System.out.println("Search ended, nodes found: " + nodes.size());


//        }).start();
        System.out.println("done");
    }

    public void render(final SpriteBatch batch) {


        if (pathFindingStarted) {
            boolean finished = false;
            for (int i = 0; !finished && i < PATH_RENDERING_REFRESH_RATE; i++) {
                finished = bfs2.update(progress);
                pathFindingStarted = !finished;
            }

        }


        batch.begin();

        for (final PathGraphNode node : progress) {
            if (node.getState() != null) {
                batch.draw(AtlasHelper.INSTANCE.findRegion(node.getState().name().toLowerCase()), node.getX(),
                        node.getY(), 1, 1);
            }
//                batch.draw(AtlasHelper.INSTANCE.findRegion("path"), node.getX(), node.getY(), 1, 1);
//            }

        }
        batch.end();
    }

    public void clearPaths() {
        path.clear();
    }
}
