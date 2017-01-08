package com.smolnij.research.scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.input.DrawMazeClickListener;

public class ControlPanel extends Stage {

    public ControlPanel(final Viewport viewport, final SpriteBatch sb) {
        super(viewport, sb);
        final Table table = new Table();
        table.top();
        table.setFillParent(true);

        final Button drawMaze = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("draw-maze-btn"));
        drawMaze.addListener(new DrawMazeClickListener());
        table.addActor(drawMaze);
        table.pack();

        addActor(table);

    }

    public void update(float dt) {

    }

}