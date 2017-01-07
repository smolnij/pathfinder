package com.smolnij.research.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class MazeRenderer implements Disposable {

    private final Texture bkgTile;
    final Batch batch;
    private final Camera camera;

    public MazeRenderer(final SpriteBatch spriteBatch) {
        bkgTile = new Texture("mainTile16.png");
        batch = spriteBatch;
        camera = new OrthographicCamera(640, 480);
    }

    public void render() {
       /* Camera camera = viewport.getCamera();
        camera.update();

        if (!root.isVisible()) return;

        Batch batch = this.batch;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        root.draw(batch, 1);
        batch.end();*/

        camera.update();
        batch.setProjectionMatrix(camera.combined);
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
    public void dispose() {
        bkgTile.dispose();
    }
}
