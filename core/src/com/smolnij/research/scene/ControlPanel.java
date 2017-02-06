package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.layout.SkinHolder;
import com.smolnij.research.pathfinding.algorithms.*;

import java.util.Arrays;

import static com.smolnij.research.pathfinding.PathfinderByNameResolver.getPathFinderByName;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer, final PathRenderer pathRenderer) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        final ButtonGroup<Button> algorithmButtonGroup = addAlgorithmButtons(table);
        final ButtonGroup<Button> heuristicButtonGroup = addHeuristicButtons(table);

        final Button findPath = createImageButton("find-path-btn");
        findPath.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                final PathFinder pathFinder = getPathFinderByName(algorithmButtonGroup.getChecked().getName(),
                        heuristicButtonGroup.getChecked().getName());
                pathRenderer.findPath(pathFinder);
                return true;
            }
        });
        findPath.left();
        table.add(findPath).expandX();

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

    private ButtonGroup<Button> addHeuristicButtons(final Table table) {

        final String[] heuristicNames = Arrays.stream(ImplementedHeuristics.values())
                .map(ImplementedHeuristics::getName).toArray(String[]::new);

        return addRadioOptionsToTable(table, heuristicNames);
    }

    private ButtonGroup<Button> addAlgorithmButtons(final Table table) {
        return addRadioOptionsToTable(table, BreadthFirstPathFinder.NAME, BestFirstPathFinder.NAME, AStarPathFinder.NAME);
    }

    private ButtonGroup<Button> addRadioOptionsToTable(final Table table, final String... options) {


        final ButtonGroup<Button> algorithmsGroup = new ButtonGroup<>();
        final VerticalGroup verticalGroup = new VerticalGroup();

        for (final String option : options) {
            final CheckBox checkbox = createLeftAlignedCheckbox(option);
            algorithmsGroup.add(checkbox);
            verticalGroup.addActor(checkbox);
        }

        algorithmsGroup.setMaxCheckCount(1);
        algorithmsGroup.setMinCheckCount(1);
        algorithmsGroup.setUncheckLast(true);
        algorithmsGroup.setChecked(algorithmsGroup.getButtons().iterator().next().getName());

        verticalGroup.fill().left().pad(5);
        table.add(verticalGroup).left();
        return algorithmsGroup;
    }

    private CheckBox createLeftAlignedCheckbox(String greedyBestFirst1) {
        final CheckBox greedyBestFirst = new CheckBox(greedyBestFirst1, SkinHolder.INSTANCE.getAppSkin());
        greedyBestFirst.setName(greedyBestFirst1);
        greedyBestFirst.left();
        return greedyBestFirst;
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

    private Button createImageButton(final String buttonTextureName) {
        return new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName),
                AtlasHelper.INSTANCE.createSpriteDrawable(buttonTextureName + "-down"));
    }

}