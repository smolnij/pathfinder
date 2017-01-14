package com.smolnij.research;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;
import com.smolnij.research.pathfinding.datastructure.IndexedNodeGraph;
import com.smolnij.research.pathfinding.heuristic.ManhattanDistance;
import com.smolnij.research.scene.ControlPanel;
import com.smolnij.research.scene.MazeRenderer;
import com.smolnij.research.scene.TiledMapPoint;
import com.smolnij.research.state.GameState;

public class PathFindingResearchApp extends ApplicationAdapter {
    public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 480;
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


    @Override
    public void create() {
        batch = new SpriteBatch();
        batch.disableBlending();

        controlPanel = new ControlPanel(new FitViewport(Gdx.graphics.getWidth(), VIRTUAL_HEIGHT, new OrthographicCamera()), batch);


        map = new TmxMapLoader().load("grid.tmx");

        prepareMap(map);

        tiledMapRenderer = setUpMapRenderer(map);

        final Node[][] maze = initMaze();


        mazeRenderer = new MazeRenderer(batch, mapCamera,
                new TiledMapPoint(START_X, START_Y), new TiledMapPoint(TARGET_X, TARGET_Y), maze);

        Gdx.input.setInputProcessor(new InputMultiplexer(controlPanel, mazeRenderer));

    }

    private Node[][] initMaze() {
        final Node[][] maze = new Node[getMapWidth(map)][getMapHeight(map)];
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[0].length; y++) {
                maze[x][y] = new Node(x, y, false);
            }
        }
        return maze;
    }

    private void searchPath(final Node[][] maze) {
        IndexedAStarPathFinder<Node> pf = new IndexedAStarPathFinder<>(new IndexedNodeGraph(maze));

        final GraphPath<Node> objects = new DefaultGraphPath<>();
        final Heuristic<Node> heuristic = new ManhattanDistance<>();

        pf.searchNodePath(new Node(0, 0, false), new Node(0, 0, false), heuristic, objects);
    }

    private void prepareMap(final TiledMap tiledMap) {
        final MapProperties mapProperties = map.getProperties();
        final int mapTileWidth = mapProperties.get("tilewidth", Integer.class);
        final int mapTileHeight = mapProperties.get("tileheight", Integer.class);

        final TiledMapTileLayer wallsAndActionLayer = new TiledMapTileLayer(getMapWidth(tiledMap), getMapHeight(tiledMap), mapTileWidth, mapTileHeight);
        wallsAndActionLayer.setName("wallsAndAction");

        addStart(wallsAndActionLayer);
        addTarget(wallsAndActionLayer);

        tiledMap.getLayers().add(wallsAndActionLayer);
    }

    private Integer getMapHeight(final TiledMap tiledMap) {
        final MapProperties mapProperties = tiledMap.getProperties();
        return mapProperties.get("height", Integer.class);
    }

    private Integer getMapWidth(final TiledMap tiledMap) {
        final MapProperties mapProperties = tiledMap.getProperties();
        return mapProperties.get("width", Integer.class);
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
        mapCamera.setToOrtho(true, getMapWidth(map), getMapHeight(map));
        mapCamera.update();
        final TiledMapRenderer renderer = new OrthoCachedTiledMapRenderer(map, 1 / 16f);
        renderer.setView(mapCamera);
        return renderer;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HdpiUtils.glViewport(0, PANEL_HEIGHT, Gdx.graphics.getWidth(), VIRTUAL_HEIGHT);
        tiledMapRenderer.render();
        if (GameState.INSTANCE.getCurrentState() == GameState.State.DRAW_MAZE) {
            mazeRenderer.render();
        }

        controlPanel.getViewport().apply();
        controlPanel.act();
        controlPanel.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        controlPanel.dispose();
        AtlasHelper.INSTANCE.disposeAtlas();
    }
}
