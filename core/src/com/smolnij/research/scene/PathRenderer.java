package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.map.TiledMapPoint;
import com.smolnij.research.pathfinding.algorithms.PathFinder;
import com.smolnij.research.pathfinding.graph.StatefulGraphMapNode;

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
    private final OrthographicCamera mapCamera;

    public PathRenderer(final MazeRenderer mazeRenderer, final OrthographicCamera mapCamera, final TiledMapPoint start, final TiledMapPoint end) {
        this.mazeRenderer = mazeRenderer;
        this.mapCamera = mapCamera;
        this.start = mazeRenderer.getMaze()[start.x][start.y];
        this.end = mazeRenderer.getMaze()[end.x][end.y];
    }

    public void findPath(final PathFinder pathFinder) {
        clearPath();
        this.pathFinder = pathFinder;
        this.pathFinder.init(start, end, mazeRenderer.getMaze());
        pathFindingStarted = true;
    }

    public void render(final SpriteBatch batch) {
        batch.setProjectionMatrix(mapCamera.combined);
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
