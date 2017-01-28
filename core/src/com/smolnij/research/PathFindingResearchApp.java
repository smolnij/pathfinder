package com.smolnij.research;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.scene.ControlPanel;
import com.smolnij.research.scene.MazeRenderer;
import com.smolnij.research.scene.PathFinder;
import com.smolnij.research.scene.TiledMapPoint;

import static com.smolnij.research.scene.MazeRenderer.*;

public class PathFindingResearchApp extends ApplicationAdapter {
    public static final int VIRTUAL_WIDTH = MAP_WIDTH * TILE_WIDTH;
    public static final int VIRTUAL_HEIGHT = MAP_HEIGHT * TILE_HEIGHT;
    public static final int PANEL_HEIGHT = 100;

    private static final int START_X = 10;
    private static final int START_Y = 15;
    private static final int TARGET_X = 40;
    private static final int TARGET_Y = 15;

    private SpriteBatch batch;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMap map;
    private ControlPanel controlPanel;
    private OrthographicCamera mapCamera;
    private MazeRenderer mazeRenderer;
    private PathFinder pathFinder;


    @Override
    public void create() {
        batch = new SpriteBatch();


        map = new TmxMapLoader().load("grid.tmx");

        prepareMap(map);

        tiledMapRenderer = setUpMapRenderer(map);

        final Node[][] maze = initMaze();


        mazeRenderer = new MazeRenderer(batch, mapCamera,
                new TiledMapPoint(START_X, START_Y), new TiledMapPoint(TARGET_X, TARGET_Y), maze);

        pathFinder = new PathFinder(mazeRenderer, new TiledMapPoint(START_X, START_Y), new TiledMapPoint(TARGET_X, TARGET_Y));

        controlPanel = new ControlPanel(new FitViewport(Gdx.graphics.getWidth(), VIRTUAL_HEIGHT + PANEL_HEIGHT), batch,
                mazeRenderer, pathFinder);

        Gdx.input.setInputProcessor(new InputMultiplexer(controlPanel, mazeRenderer));

    }

    private Node[][] initMaze() {
        final Node[][] maze = new Node[MAP_WIDTH][MAP_HEIGHT];
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[0].length; y++) {
                maze[x][y] = new Node(x, y, false);
            }
        }
        return maze;
    }

    private void prepareMap(final TiledMap tiledMap) {
        final TiledMapTileLayer wallsAndActionLayer = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        wallsAndActionLayer.setName("wallsAndAction");

        addStart(wallsAndActionLayer);
        addTarget(wallsAndActionLayer);

        tiledMap.getLayers().add(wallsAndActionLayer);
    }

    private void addStart(final TiledMapTileLayer wallsAndActionLayer) {
        addCell(wallsAndActionLayer, START_X, START_Y, "start");
    }

    private void addTarget(final TiledMapTileLayer wallsAndActionLayer) {
        addCell(wallsAndActionLayer, TARGET_X, TARGET_Y, "target");
    }

    private void addCell(final TiledMapTileLayer wallsAndActionLayer, final int x, final int y, final String textureName) {
        final TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(AtlasHelper.INSTANCE.findRegion(textureName)));
        wallsAndActionLayer.setCell(x, y, cell);
    }

    private TiledMapRenderer setUpMapRenderer(final TiledMap map) {
        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(true, MAP_WIDTH, MAP_HEIGHT);
        mapCamera.update();
        final TiledMapRenderer renderer = new OrthoCachedTiledMapRenderer(map, 1 / 16f);
        renderer.setView(mapCamera);
        return renderer;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HdpiUtils.glViewport(0, PANEL_HEIGHT, Gdx.graphics.getWidth(), VIRTUAL_HEIGHT);
        tiledMapRenderer.render();

        batch.enableBlending();
        mazeRenderer.render();
        pathFinder.render(batch);
        controlPanel.getViewport().apply();
        controlPanel.act();
        controlPanel.draw();
        batch.disableBlending();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        controlPanel.dispose();
        AtlasHelper.INSTANCE.disposeAtlas();
    }
}
