package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.MapNode;
import com.smolnij.research.pathfinding.algorithms.BestFirstPathFinder;
import com.smolnij.research.pathfinding.algorithms.PathFinder;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;
import com.smolnij.research.pathfinding.heuristic.ManhattanDistance;

import java.util.HashSet;
import java.util.Set;

public class PathRenderer {
    private static final int PATH_RENDERING_REFRESH_RATE = 1;
    private Set<StatefulGraphMapNode> progress = new HashSet<>();

    private final MapNode start;
    private final MapNode end;
    private boolean pathFindingStarted = false;
    private final MazeRenderer mazeRenderer;
    private PathFinder pathFinder;

    public PathRenderer(final MazeRenderer mazeRenderer, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath(final PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.pathFinder.init(start, end, mazeRenderer.getMaze());
        pathFindingStarted = true;
    }

    public void findPathGreedyBestFirst() {
        pathFinder = new BestFirstPathFinder(new ManhattanDistance<>());
        pathFinder.init(start, end, mazeRenderer.getMaze());
        pathFindingStarted = true;
    }


    public void render(final SpriteBatch batch) {

        boolean finished = false;
        if (pathFindingStarted) { //GWT has no support for standard multithreading, hence the refresh rate used
            for (int i = 0; !finished && i < PATH_RENDERING_REFRESH_RATE; i++) {
                finished = pathFinder.update(progress);
                pathFindingStarted = !finished;
            }
        }

        batch.begin();
        for (final StatefulGraphMapNode node : progress) {
            if (node.getState() != null) {
                batch.draw(AtlasHelper.INSTANCE.findRegion(node.getState().name().toLowerCase()), node.getX(),
                        node.getY(), 1, 1);
            }
        }
        batch.end();
    }

    public void clearPath() {
        progress.clear();
    }
}
