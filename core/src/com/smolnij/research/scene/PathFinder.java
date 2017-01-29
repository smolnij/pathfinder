package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.algorithms.BestFirstSearch;
import com.smolnij.research.pathfinding.algorithms.PathGraphNode;

import java.util.HashSet;
import java.util.Set;

public class PathFinder {
    private static final int PATH_RENDERING_REFRESH_RATE = 1;
    private Set<PathGraphNode> progress = new HashSet<>();

    private final Node start;
    private final Node end;
    private boolean pathFindingStarted = false;
    private final MazeRenderer mazeRenderer;
    private BestFirstSearch bestFirstSearch;

    public PathFinder(final MazeRenderer mazeRenderer, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath() {
        final PathGraphNode[][] graph = buildGraph();
        bestFirstSearch = new BestFirstSearch();
        bestFirstSearch.init(graph[start.getX()][start.getY()], graph[end.getX()][end.getY()], graph);
        pathFindingStarted = true;
    }

    private PathGraphNode[][] buildGraph() {
        final PathGraphNode[][] graph = new PathGraphNode[mazeRenderer.getMaze().length][mazeRenderer.getMaze()[0].length];
        for (int i = 0; i < mazeRenderer.getMaze().length; i++) {
            for (int j = 0; j < mazeRenderer.getMaze()[0].length; j++) {
                graph[i][j] = new PathGraphNode(i, j, mazeRenderer.getMaze()[i][j].isBlocked());
            }
        }
        return graph;
    }

    public void render(final SpriteBatch batch) {

        boolean finished = false;
        if (pathFindingStarted) { //GWT has no suppurt for standard multithreading, hence the refresh rate used
            for (int i = 0; !finished && i < PATH_RENDERING_REFRESH_RATE; i++) {
                finished = bestFirstSearch.update(progress);
                pathFindingStarted = !finished;
            }
        }

        batch.begin();
        for (final PathGraphNode node : progress) {
            if (node.getState() != null) {
                batch.draw(AtlasHelper.INSTANCE.findRegion(node.getState().name().toLowerCase()), node.getX(),
                        node.getY(), 1, 1);
            }
        }
        batch.end();
    }

    public void clearPaths() {
        progress.clear();
    }
}
