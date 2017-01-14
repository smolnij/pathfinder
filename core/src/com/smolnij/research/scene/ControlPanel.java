package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.input.DrawWallClickListener;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb) {
        super(viewport, sb);
        final Table table = new Table();
        table.top();
        table.setFillParent(true);

        final Button drawWalls = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("draw-walls-btn-down"));
        drawWalls.addListener(new DrawWallClickListener());
        table.addActor(drawWalls);

        final Button findPath = new ImageButton(
                AtlasHelper.INSTANCE.createSpriteDrawable("search-path-btn"),
                AtlasHelper.INSTANCE.createSpriteDrawable("search-path-btn-down"));


        table.addActor(findPath);

        table.pack();

        addActor(table);

    }

    public void update(float dt) {

    }

}