package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.input.DrawWallClickListener;
import com.smolnij.research.scene.input.FindPathClickListener;
import com.smolnij.research.scene.input.RemoveWallsClickListener;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb, final MazeRenderer mazeRenderer) {
        super(viewport, sb);
        final Table table = new Table();
        table.bottom().left();

        table.setFillParent(true);

        final Button drawWalls = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn-down"));
        drawWalls.addListener(new DrawWallClickListener(mazeRenderer));
        table.add(drawWalls).pad(10);

        final Button removeWalls = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("remove-walls-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("remove-walls-btn-down"));
        removeWalls.addListener(new RemoveWallsClickListener(mazeRenderer));
        table.add(removeWalls).pad(10);

        final Button findPath = new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable("search-path-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("search-path-btn-down"));

        findPath.addListener(new FindPathClickListener());
        table.add(findPath).pad(10);

        table.pack();

        addActor(table);

    }

}