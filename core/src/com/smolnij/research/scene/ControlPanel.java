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

    private static final String BREADTH_FIRST = "Breadth First";
    private static final String GREEDY_BEST_FIRST = "Greedy Best First";
    private static final String A_STAR = "A*";
    private static final String MANHATTAN = "Manhattan";
    private static final String CHEBYSHEV = "Chebyshev";
    private static final String EUCLIDIAN = "Euclidian";

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer, final PathRenderer pathRenderer) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        addAlgorithmButtons(table);
        addHeuristicButtons(table);

        final Button findPathWithAStarButton = createFindPathWithAStarButton(pathRenderer);
        findPathWithAStarButton.left();
        table.add(findPathWithAStarButton).expandX().left();

        addHelperButtons(mazeRenderer, pathRenderer, table);

        addActor(table);
    }

    private void addHelperButtons(final MazeRenderer mazeRenderer, final PathRenderer pathRenderer, final Table table) {
        final VerticalGroup helperButtonsGroup = new VerticalGroup();
        final Button mazeGeneratorButton = createMazeGeneratorButton(mazeRenderer);
        mazeGeneratorButton.left();
        mazeGeneratorButton.pad(3);
        helperButtonsGroup.addActor(mazeGeneratorButton);

        final Button clearWallsButton = createClearWallsButton(mazeRenderer);
        clearWallsButton.left();
        clearWallsButton.pad(3);
        helperButtonsGroup.addActor(clearWallsButton);

        final Button clearPathButton = createClearPathButton(pathRenderer);
        clearPathButton.left();
        clearPathButton.pad(3);
        helperButtonsGroup.addActor(clearPathButton);
        helperButtonsGroup.fill().left().pad(5);
        table.add(helperButtonsGroup);
    }

    private void addHeuristicButtons(final Table table) {
        final CheckBox manhattan = new CheckBox(MANHATTAN, SkinHolder.INSTANCE.getAppSkin());
        manhattan.left();
        final CheckBox chebyshev = new CheckBox(CHEBYSHEV, SkinHolder.INSTANCE.getAppSkin());
        chebyshev.left();
        final CheckBox euclidian = new CheckBox(EUCLIDIAN, SkinHolder.INSTANCE.getAppSkin());
        euclidian.left();

        final ButtonGroup<Button> heuristicGroup = new ButtonGroup<>(manhattan, chebyshev, euclidian);
        heuristicGroup.setMaxCheckCount(1);
        heuristicGroup.setMinCheckCount(1);
        heuristicGroup.setUncheckLast(true);
        heuristicGroup.setChecked(MANHATTAN);

        final VerticalGroup heuristicVerticalGroup = new VerticalGroup();

        heuristicVerticalGroup.addActor(manhattan);
        heuristicVerticalGroup.addActor(chebyshev);
        heuristicVerticalGroup.addActor(euclidian);
        heuristicVerticalGroup.fill().left().pad(5);
        table.add(heuristicVerticalGroup).expandX().left();
    }

    private void addAlgorithmButtons(final Table table) {
        final CheckBox manhattan = new CheckBox(BREADTH_FIRST, SkinHolder.INSTANCE.getAppSkin());
        manhattan.left();
        final CheckBox chebyshev = new CheckBox(GREEDY_BEST_FIRST, SkinHolder.INSTANCE.getAppSkin());
        chebyshev.left();
        final CheckBox euclidian = new CheckBox(A_STAR, SkinHolder.INSTANCE.getAppSkin());
        euclidian.left();

        final ButtonGroup<Button> heuristicGroup = new ButtonGroup<>(manhattan, chebyshev, euclidian);
        heuristicGroup.setMaxCheckCount(1);
        heuristicGroup.setMinCheckCount(1);
        heuristicGroup.setUncheckLast(true);
        heuristicGroup.setChecked(BREADTH_FIRST);

        final VerticalGroup heuristicVerticalGroup = new VerticalGroup();

        heuristicVerticalGroup.addActor(manhattan);
        heuristicVerticalGroup.addActor(chebyshev);
        heuristicVerticalGroup.addActor(euclidian);
        heuristicVerticalGroup.fill().left();
        table.add(heuristicVerticalGroup).left();
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

    private Button createClearPathButton(final PathRenderer pathRenderer) {
        final Button clearWallsBtn = createImageButton("clear-path-btn");
        clearWallsBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pathRenderer.clearPath();
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