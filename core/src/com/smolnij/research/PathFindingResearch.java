package com.smolnij.research;

import com.badlogic.gdx.*;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.ControlPanel;
import com.smolnij.research.state.GameState;

public class PathFindingResearch extends ApplicationAdapter implements InputProcessor {
    public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 480;
    public static final int PANEL_HEIGHT = 100;

    private static final int START_X = 10;
    private static final int START_Y = 15;
    private static final int TARGET_X = 40;
    private static final int TARGET_Y = 15;

    private SpriteBatch batch;
    private TiledMapRenderer mapRenderer;
    private TiledMap map;
    private ControlPanel controlPanel;


    private int mapTileWidth;
    private int mapTileHeight;
    private OrthographicCamera mapCamera;

    private Vector3 touchPoint = new Vector3();
    private boolean dragging;


    @Override
    public void create() {
        batch = new SpriteBatch();
        batch.disableBlending();

        controlPanel = new ControlPanel(new FitViewport(Gdx.graphics.getWidth(), VIRTUAL_HEIGHT, new OrthographicCamera()), batch);


        map = new TmxMapLoader().load("grid.tmx");

        final MapProperties mapProperties = map.getProperties();
        mapTileWidth = mapProperties.get("tilewidth", Integer.class);
        mapTileHeight = mapProperties.get("tileheight", Integer.class);

        prepareMap(map);

        mapRenderer = setUpMapRenderer();

        Gdx.input.setInputProcessor(new InputMultiplexer(controlPanel, this));
//        PathFinder pf = new IndexedAStarPathFinder(null);
    }

    private void prepareMap(final TiledMap tiledMap) {
        final TiledMapTileLayer wallsAndActionLayer = new TiledMapTileLayer(getMapWidth(tiledMap), getMapHeight(tiledMap), mapTileWidth, mapTileHeight);
        wallsAndActionLayer.setName("wallsAndAction");

        addStart(wallsAndActionLayer);
        addTarget(wallsAndActionLayer);

        tiledMap.getLayers().add(wallsAndActionLayer);
    }

    private Integer getMapHeight(TiledMap tiledMap) {
        final MapProperties mapProperties = tiledMap.getProperties();
        return mapProperties.get("height", Integer.class);
    }

    private Integer getMapWidth(TiledMap tiledMap) {
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

    private TiledMapRenderer setUpMapRenderer() {
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
        mapRenderer.render();
        if (GameState.INSTANCE.getCurrentState() == GameState.State.DRAW_MAZE) {
            batch.setProjectionMatrix(mapCamera.combined);
            batch.begin();
            final Vector3 wallCursorCoords = new Vector3();
            screenToMapXY(Gdx.input.getX(), Gdx.input.getY(), wallCursorCoords);
            batch.draw(AtlasHelper.INSTANCE.findRegion("wall"), (float) (Math.floor(wallCursorCoords.x)),
                    (float) (Math.floor(wallCursorCoords.y)), 1, 1);

            addWallSegment(touchPoint);
//            System.out.println("x: " + touchPoint.x + " y: " + touchPoint.y);
            addCell((TiledMapTileLayer) map.getLayers().get("wallsAndAction"), (int) touchPoint.x, (int) touchPoint.y, "wall");
            mapRenderer = setUpMapRenderer();
            batch.end();
        }


        controlPanel.getViewport().apply();
        controlPanel.act();
        controlPanel.draw();
    }

    private void addWallSegment(final Vector3 touchPoint) {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        dragging = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        dragging = false;
        return true;
    }

    private void screenToMapXY(final int screenX, final int screenY, final Vector3 vector) {
        mapCamera.unproject(vector.set(screenX, screenY, 0), 0, PANEL_HEIGHT, Gdx.graphics.getWidth(), VIRTUAL_HEIGHT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        controlPanel.dispose();
        AtlasHelper.INSTANCE.disposeAtlas();
    }
}
