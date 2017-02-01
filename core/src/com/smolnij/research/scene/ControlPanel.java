package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.layout.SkinHolder;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer, final PathRenderer pathRenderer) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom().left();
        table.setFillParent(true);

        table.add(createMazeGeneratorButton(mazeRenderer)).pad(5);

        table.row();
        table.add(createClearWallButton(mazeRenderer)).pad(5);
        table.add(createClearPathsButton(pathRenderer)).pad(5);
        table.row();

        final Button findPathAStarButton = createFindPathWithAStarButton(pathRenderer);
        final Button findPathBestFirstButton = createFindPathWithBestFirstButton(pathRenderer);

        final ButtonGroup<Button> pathfindingBtnGroup = new ButtonGroup<>(findPathAStarButton, findPathBestFirstButton);
        pathfindingBtnGroup.setMaxCheckCount(1);
        pathfindingBtnGroup.setMinCheckCount(1);
        pathfindingBtnGroup.setUncheckLast(true);

        table.add(findPathAStarButton).pad(5);
        table.add(findPathBestFirstButton).pad(5);

        final CheckBox cb = new CheckBox("Manhattan", SkinHolder.INSTANCE.getAppSkin());

        table.add(cb).pad(5);

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

    private Button createClearPathsButton(final PathRenderer pathRenderer) {
        final Button clearWallsBtn = createImageButton("clear-paths-btn");
        clearWallsBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pathRenderer.clearPaths();
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

    private Button createFindPathWithAStarButton(final PathRenderer pathRenderer) {
        final Button findPath = createImageButton("find-path-btn");
        //todo lambda
        findPath.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                pathRenderer.findPathAStar();
                return true;
            }
        });
        return findPath;
    }

    private Button createFindPathWithBestFirstButton(final PathRenderer pathRenderer) {
        final Button findPath = createImageButton("find-path-btn");
        //todo lambda
        findPath.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                pathRenderer.findPathGreedyBestFirst();
                return true;
            }
        });
        return findPath;
    }

    private Button createImageButton(final String buttonTextureName) {
        return new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName),
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName + "-down"));
    }

}