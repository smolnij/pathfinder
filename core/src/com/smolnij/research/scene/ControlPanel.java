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
import com.smolnij.research.scene.input.FindPathClickListener;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom().left();

        table.setFillParent(true);

        table.add(createDrawWallsButton(mazeRenderer)).pad(10);
        table.add(createRemoveWallsButton(mazeRenderer)).pad(10);

        final Button findPath = new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable("find-path-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("find-path-btn-down"));

        findPath.addListener(new FindPathClickListener());
        table.add(findPath).pad(10);

        addActor(table);

    }

    private Button createRemoveWallsButton(final MazeRenderer mazeRenderer) {
        final Button removeWalls = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("remove-wall-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("remove-wall-btn-down"));

        removeWalls.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                mazeRenderer.toRemoveWallsState();
                return true;
            }
        });
        return removeWalls;
    }

    private Button createDrawWallsButton(final MazeRenderer mazeRenderer) {
        final Button drawWalls = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn-down"));
        drawWalls.addListener(new ClickListener() {
            @Override
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                mazeRenderer.toDrawWallsState();
                return true;
            }
        });
        return drawWalls;
    }

}