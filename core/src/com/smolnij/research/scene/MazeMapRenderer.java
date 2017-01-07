package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

public class MazeMapRenderer implements MapRenderer, Disposable {

    private final SpriteBatch batch;
    private final Texture bkgTile;

    public MazeMapRenderer(final SpriteBatch batch) {
        this.batch = batch;
        bkgTile = new Texture("mainTile16.png");
    }

    @Override
    public void setView(final OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth, float viewboundsHeight) {
        batch.setProjectionMatrix(projectionMatrix);
    }

    @Override
    public void render() {
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 30; c++) {
                final int x = c * 32;
                final int y = r * 32;
                batch.draw(bkgTile, x, y);
            }
        }

        batch.end();
    }

    @Override
    public void render(int[] layers) {

    }


    @Override
    public void dispose() {
        bkgTile.dispose();
    }
}
