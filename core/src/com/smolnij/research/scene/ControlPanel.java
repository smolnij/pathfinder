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
        table.bottom();
        table.setFillParent(true);

        addAlgorithmButtons(table);
        addHeuristicButtons(table);
        addHelperButtons(mazeRenderer, pathRenderer, table);

        addActor(table);
    }

    private void addHelperButtons(final MazeRenderer mazeRenderer, final PathRenderer pathRenderer, final Table table) {
        final VerticalGroup helperButtonsGroup = new VerticalGroup();
        final Button mazeGeneratorButton = createMazeGeneratorButton(mazeRenderer);
        mazeGeneratorButton.left();
        helperButtonsGroup.addActor(mazeGeneratorButton);

        final Button clearWallsButton = createClearWallsButton(mazeRenderer);
        clearWallsButton.left();
        helperButtonsGroup.addActor(clearWallsButton);

        final Button clearPathsButton = createClearPathsButton(pathRenderer);
        clearPathsButton.left();
        helperButtonsGroup.addActor(clearPathsButton);
        helperButtonsGroup.fill().left();
        table.add(helperButtonsGroup);
    }

    private void addHeuristicButtons(final Table table) {
        final CheckBox manhattan = new CheckBox("Manhattan", SkinHolder.INSTANCE.getAppSkin());
        manhattan.left();
        final CheckBox chebyshev = new CheckBox("Chebyshev", SkinHolder.INSTANCE.getAppSkin());
        chebyshev.left();
        final CheckBox euclidian = new CheckBox("Euclidian", SkinHolder.INSTANCE.getAppSkin());
        euclidian.left();

        final ButtonGroup<Button> heuristicGroup = new ButtonGroup<>(manhattan, chebyshev, euclidian);
        heuristicGroup.setMaxCheckCount(1);
        heuristicGroup.setMinCheckCount(1);
        heuristicGroup.setUncheckLast(true);

        final VerticalGroup heuristicVerticalGroup = new VerticalGroup();

        heuristicVerticalGroup.addActor(manhattan);
        heuristicVerticalGroup.addActor(chebyshev);
        heuristicVerticalGroup.addActor(euclidian);
        heuristicVerticalGroup.fill().left().pad(5);
        table.add(heuristicVerticalGroup);
    }

    private void addAlgorithmButtons(final Table table) {
        final CheckBox manhattan = new CheckBox("Breadth First", SkinHolder.INSTANCE.getAppSkin());
        manhattan.left();
        final CheckBox chebyshev = new CheckBox("Greedy Best First", SkinHolder.INSTANCE.getAppSkin());
        chebyshev.left();
        final CheckBox euclidian = new CheckBox("A*", SkinHolder.INSTANCE.getAppSkin());
        euclidian.left();

        final ButtonGroup<Button> heuristicGroup = new ButtonGroup<>(manhattan, chebyshev, euclidian);
        heuristicGroup.setMaxCheckCount(1);
        heuristicGroup.setMinCheckCount(1);
        heuristicGroup.setUncheckLast(true);

        final VerticalGroup heuristicVerticalGroup = new VerticalGroup();

        heuristicVerticalGroup.addActor(manhattan);
        heuristicVerticalGroup.addActor(chebyshev);
        heuristicVerticalGroup.addActor(euclidian);
        heuristicVerticalGroup.fill().left().pad(5);
        table.add(heuristicVerticalGroup).expandX().left();
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

    private Button createClearWallsButton(final MazeRenderer mazeRenderer) {
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