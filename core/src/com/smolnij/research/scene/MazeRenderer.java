package com.smolnij.research.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.smolnij.research.layout.AtlasHelper;

import static com.smolnij.research.PathFindingResearchApp.PANEL_HEIGHT;
import static com.smolnij.research.PathFindingResearchApp.VIRTUAL_HEIGHT;

public class MazeRenderer implements InputProcessor {

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Vector3 touchPoint = new Vector3(-1, -1, -1);
    private final Vector3 wallCursorCoords = new Vector3();
    private final boolean[][] wallSegments;
    private boolean dragging;

    public MazeRenderer(final SpriteBatch batch, final OrthographicCamera camera, final int mazeWidth, final int mazeHeight) {
        this.batch = batch;
        this.camera = camera;
        wallSegments = new boolean[mazeWidth][mazeHeight];
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        screenToMapXY(Gdx.input.getX(), Gdx.input.getY(), wallCursorCoords);
        batch.draw(AtlasHelper.INSTANCE.findRegion("wall"), (float) (Math.floor(wallCursorCoords.x)),
                (float) (Math.floor(wallCursorCoords.y)), 1, 1);

        for (int i = 0; i < wallSegments.length; i++) {
            for (int j = 0; j < wallSegments[0].length; j++) {
                if (wallSegments[i][j]) {
                    batch.draw(AtlasHelper.INSTANCE.findRegion("wall"), i, j, 1, 1);
                }
            }
        }
        batch.end();
    }

    private void addWallSegment(final Vector3 touchPoint) {
        final int x = (int) touchPoint.x;
        final int y = (int) touchPoint.y;

        if (x < 0 || x > wallSegments.length || y < 0 || y > wallSegments[0].length) {
            return;
        }

        wallSegments[x][y] = true;
    }


    @Override
    public boolean keyDown(final int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        addWallSegment(touchPoint);
        dragging = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        addWallSegment(touchPoint);
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
        camera.unproject(vector.set(screenX, screenY, 0), 0, PANEL_HEIGHT, Gdx.graphics.getWidth(), VIRTUAL_HEIGHT);
    }
}
