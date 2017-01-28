package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer, final PathFinder pathFinder) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom().left();
        table.setFillParent(true);

        table.add(createMazeGeneratorButton(mazeRenderer)).pad(5);
        table.add(createDrawWallsButton(mazeRenderer, pathFinder)).pad(5);
        table.add(createRemoveWallButton(mazeRenderer)).pad(5);
        table.add(createFindPathButton(pathFinder)).pad(5);
        table.row();
        table.add(createClearWallButton(mazeRenderer)).pad(5);
        table.add(createClearPathsButton(pathFinder)).pad(5);

        addActor(table);

    }

    private Button createMazeGeneratorButton(final MazeRenderer mazeRenderer) {
        final Button generateMazeBtn = createImageButton("generate-maze-btn");
        generateMazeBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mazeRenderer.generateMaze();
                return true;
            }
        });
        return generateMazeBtn;
    }

    private Button createClearPathsButton(final PathFinder pathFinder) {
        final Button clearWallsBtn = createImageButton("clear-paths-btn");
        clearWallsBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pathFinder.clearPaths();
                return true;
            }
        });
        return clearWallsBtn;
    }

    private Button createClearWallButton(final MazeRenderer mazeRenderer) {
        final Button clearWallsBtn = createImageButton("clear-walls-btn");
        clearWallsBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mazeRenderer.clearWalls();
                return true;
            }
        });
        return clearWallsBtn;
    }

    private Button createFindPathButton(final PathFinder pathFinder) {
        final Button findPath = createImageButton("find-path-btn");
        findPath.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                pathFinder.findPath();
                return true;
            }
        });
        return findPath;
    }

    private Button createRemoveWallButton(final MazeRenderer mazeRenderer) {
        final Button removeWall = createImageButton("remove-wall-btn");
        removeWall.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                mazeRenderer.toRemoveWallsState();
                return true;
            }
        });
        return removeWall;
    }

    private Button createDrawWallsButton(final MazeRenderer mazeRenderer, final PathFinder pathFinder) {
        final Button drawWalls = createImageButton("draw-walls-btn");
        drawWalls.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                pathFinder.clearPaths();
                mazeRenderer.toDrawWallsState();
                return true;
            }
        });
        return drawWalls;
    }

    private Button createImageButton(final String buttonTextureName) {
        return new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName),
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName + "-down"));
    }

}