package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapStage extends Stage {

    private TiledMapRenderer mapRenderer;
    private TiledMap map;

    public MapStage() {


        map = new TmxMapLoader().load("grid.tmx");
        mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setView((OrthographicCamera) super.getCamera());

    }

    @Override
    public void draw() {
        super.draw();
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        super.dispose();
    }
}
