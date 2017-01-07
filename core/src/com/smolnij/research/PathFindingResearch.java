package com.smolnij.research;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
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


    @Override
    public void create() {
        batch = new SpriteBatch();

        controlPanel = new ControlPanel(batch);
        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, MAP_WIDTH, MAP_HEIGHT);
        mapCamera.update();

        map = new TmxMapLoader().load("grid.tmx");
        mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setView(mapCamera);

        Gdx.input.setInputProcessor(controlPanel);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controlPanel.act();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), 50);
        controlPanel.draw();

        Gdx.gl.glViewport(0, 50, Gdx.graphics.getWidth(), 480);
        mapRenderer.render();

        if (GameState.INSTANCE.getCurrentState() == GameState.State.DRAW_MAZE) {
            batch.draw(AtlasHelper.INSTANCE.getTexture("wall"), Gdx.input.getX(), Gdx.input.getY());
            System.out.println("drwa");
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        controlPanel.dispose();
        AtlasHelper.INSTANCE.disposeAtlas();
    }
}
