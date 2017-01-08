package com.smolnij.research;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.ControlPanel;
import com.smolnij.research.state.GameState;

public class PathFindingResearch extends ApplicationAdapter {
    private SpriteBatch batch;
    private TiledMapRenderer mapRenderer;
    private TiledMap map;
    private OrthographicCamera mapCamera;
    private ControlPanel controlPanel;

    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 480;
    public static final int PANEL_HEIGHT = 50;
    private int mapTileWidth;
    private int mapTileHeight;


    @Override
    public void create() {
        batch = new SpriteBatch();

        controlPanel = new ControlPanel(new FitViewport(Gdx.graphics.getWidth(), MAP_HEIGHT, new OrthographicCamera()), batch);
        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(true, MAP_WIDTH, MAP_HEIGHT);
        mapCamera.update();

        map = new TmxMapLoader().load("grid.tmx");
        final MapProperties mapProperties = map.getProperties();
        mapTileWidth = mapProperties.get("tilewidth", Integer.class);
        mapTileHeight = mapProperties.get("tileheight", Integer.class);
        mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setView(mapCamera);

        Gdx.input.setInputProcessor(controlPanel);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HdpiUtils.glViewport(0, PANEL_HEIGHT, Gdx.graphics.getWidth(), MAP_HEIGHT);
        mapRenderer.render();
        if (GameState.INSTANCE.getCurrentState() == GameState.State.DRAW_MAZE) {
            batch.begin();
            batch.draw(AtlasHelper.INSTANCE.findRegion("wall"), Gdx.input.getX() - mapTileWidth / 2, MAP_HEIGHT - Gdx.input.getY() - mapTileHeight / 2);
            batch.end();
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
