package com.smolnij.research.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.map.MapNode;
import com.smolnij.research.map.TiledMapPoint;
import com.smolnij.research.maze.MazeGenerator;

import static com.smolnij.research.PathFindingResearchApp.PANEL_HEIGHT;
import static com.smolnij.research.PathFindingResearchApp.VIRTUAL_HEIGHT;

public class MazeRenderer implements InputProcessor {

    public static final int MAP_WIDTH = 49;
    public static final int MAP_HEIGHT = 29;
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Vector3 touchPoint = new Vector3(-1, -1, -1);
    private final Vector3 currentCursorCoords = new Vector3();
    private final TiledMapPoint startPoint;
    private final TiledMapPoint targetPoint;
    private Vector2 lastTouchPoint = new Vector2(-1, -1);
    private MapNode[][] maze;
    private boolean dragging;
    private boolean removeWall;
    private MazeGenerator mazeGenerator;
    private final TextureRegion drawWallCursor = AtlasHelper.INSTANCE.findRegion("draw-wall-cursor");
    private final TextureRegion removeWallCursor = AtlasHelper.INSTANCE.findRegion("remove-wall-cursor");

    public MazeRenderer(final SpriteBatch batch, final OrthographicCamera camera,
                        final TiledMapPoint startPoint,
                        final TiledMapPoint targetPoint, final MapNode[][] maze) {
        this.batch = batch;
        this.camera = camera;
        this.startPoint = startPoint;
        this.targetPoint = targetPoint;
        this.maze = maze;
        mazeGenerator = new MazeGenerator(MAP_WIDTH, MAP_HEIGHT);
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawMaze();

        screenToMapXY(Gdx.input.getX(), Gdx.input.getY(), currentCursorCoords);
        if (beyondMazeBoundaries((int) currentCursorCoords.x, (int) currentCursorCoords.y)) {
            batch.end();
            return;
        }
        final boolean cursorOverWall = maze[(int) currentCursorCoords.x][(int) currentCursorCoords.y].isBlocked();


        if (dragging) {
            batch.draw(cursorOverWall ? drawWallCursor : removeWallCursor, (float) (Math.floor(currentCursorCoords.x)), (float) (Math.floor(currentCursorCoords.y)), 1, 1);
        } else {
            batch.draw(cursorOverWall ? removeWallCursor : drawWallCursor, (float) (Math.floor(currentCursorCoords.x)), (float) (Math.floor(currentCursorCoords.y)), 1, 1);
        }
        batch.end();
    }

    private void drawMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                final MapNode node = maze[i][j];
                if (node.isBlocked()) {
                    batch.draw(AtlasHelper.INSTANCE.getWallTexture(), i, j, 1, 1);
                }
            }
        }
    }

    private void setWalls(final MapNode[][] maze) {
        this.maze = maze;
    }

    public MapNode[][] getMaze() {
        return maze;
    }

    public void generateMaze() {
        mazeGenerator.generateMaze(startPoint.x, startPoint.y, targetPoint.x, targetPoint.y);
        setWalls(mazeGenerator.getGrid());
    }

    public void clearWalls() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new MapNode(i, j, false);
            }
        }
    }

    private void editWallSegment(final Vector3 touchPoint) {
        final int x = (int) touchPoint.x;
        final int y = (int) touchPoint.y;
        if ((int) lastTouchPoint.x == x && (int) lastTouchPoint.y == y) {
            return;
        }

        if (isStartPoint(x, y) || isTargetPoint(x, y)) {
            return;
        }

        if (beyondMazeBoundaries(x, y)) {
            return;
        }
        maze[x][y] = new MapNode(x, y, !removeWall);
        lastTouchPoint.x = x;
        lastTouchPoint.y = y;
    }

    private boolean beyondMazeBoundaries(int x, int y) {
        return x < 0 || x >= maze.length || y < 0 || y >= maze[0].length;
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
        if (beyondMazeBoundaries((int) touchPoint.x, (int) touchPoint.y)) {
            return false;
        }

        final MapNode currentCell = maze[(int) touchPoint.x][(int) touchPoint.y];
        removeWall = currentCell.isBlocked();
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
