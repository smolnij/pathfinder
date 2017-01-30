package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.algorithms.AStarPathFinder;
import com.smolnij.research.pathfinding.algorithms.PathFinder;
import com.smolnij.research.pathfinding.algorithms.PathGraphNode;

import java.util.HashSet;
import java.util.Set;

public class PathRenderer {
    private static final int PATH_RENDERING_REFRESH_RATE = 1;
    private Set<PathGraphNode> progress = new HashSet<>();

    private final Node start;
    private final Node end;
    private boolean pathFindingStarted = false;
    private final MazeRenderer mazeRenderer;
    private PathFinder bestFirstSearch;

    public PathRenderer(final MazeRenderer mazeRenderer, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath() {
        bestFirstSearch = new AStarPathFinder();
//        bestFirstSearch = new BestFirstPathFinder();
        bestFirstSearch.init(start, end, mazeRenderer.getMaze());
        pathFindingStarted = true;
    }


    public void render(final SpriteBatch batch) {

        boolean finished = false;
        if (pathFindingStarted) { //GWT has no support for standard multithreading, hence the refresh rate used
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
