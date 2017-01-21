package com.smolnij.research.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.pathfinding.Node;

import static com.smolnij.research.PathFindingResearchApp.PANEL_HEIGHT;
import static com.smolnij.research.PathFindingResearchApp.VIRTUAL_HEIGHT;

public class MazeRenderer implements InputProcessor {

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Vector3 touchPoint = new Vector3(-1, -1, -1);
    private final Vector3 wallCursorCoords = new Vector3();
    private final TiledMapPoint startPoint;
    private final TiledMapPoint targetPoint;
    private Node[][] maze;
    private boolean dragging;
    private TextureRegion cursor;
    private boolean blockCell;

    public MazeRenderer(final SpriteBatch batch, final OrthographicCamera camera,
                        final TiledMapPoint startPoint,
                        final TiledMapPoint targetPoint, final Node[][] maze) {
        this.batch = batch;
        this.camera = camera;
        this.startPoint = startPoint;
        this.targetPoint = targetPoint;
        this.maze = maze;
        toWaitingState();
    }

    public void toWaitingState() {
        cursor = null;
    }

    private boolean isWaitingState() {
        return cursor == null;
    }

    public void toDrawWallsState() {
        cursor = AtlasHelper.INSTANCE.findRegion("draw-wall-cursor");
        blockCell = true;
    }

    public void toRemoveWallsState() {
        cursor = AtlasHelper.INSTANCE.findRegion("remove-wall-cursor");
        blockCell = false;
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawMaze();

        screenToMapXY(Gdx.input.getX(), Gdx.input.getY(), wallCursorCoords);
        if (!isWaitingState()) {
            batch.draw(cursor, (float) (Math.floor(wallCursorCoords.x)), (float) (Math.floor(wallCursorCoords.y)), 1, 1);
        }


        batch.end();
    }

    private void drawMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].isBlocked()) {
                    batch.draw(AtlasHelper.INSTANCE.getWallTexture(), i, j, 1, 1);
                }
            }
        }
    }

    public void setWalls(final Node[][] maze) {
        this.maze = maze;
    }

    public void clearWalls() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Node(i, j, false);
            }
        }
    }

    private void editWallSegment(final Vector3 touchPoint) {
        final int x = (int) touchPoint.x;
        final int y = (int) touchPoint.y;
        if (isStartPoint(x, y) || isTargetPoint(x, y)) {
            return;
        }

        if (x < 0 || x > maze.length - 1 || y < 0 || y > maze[0].length - 1) {
            return;
        }

        maze[x][y] = new Node(x, y, blockCell);

    }

    private boolean isTargetPoint(int x, int y) {
        return x == targetPoint.x && y == targetPoint.y;
    }

    private boolean isStartPoint(int x, int y) {
        return x == startPoint.x && y == startPoint.y;
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
        editWallSegment(touchPoint);
        dragging = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        screenToMapXY(screenX, screenY, touchPoint);
        editWallSegment(touchPoint);
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
