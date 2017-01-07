package com.smolnij.research.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smolnij.research.layout.AtlasHelper;
import com.smolnij.research.scene.input.DrawMazeClickListener;

public class ControlPanel extends Stage {

    public ControlPanel(final SpriteBatch sb) {
        super(new FitViewport(Gdx.graphics.getWidth(), 50, new OrthographicCamera()), sb);
        //setup the HUD viewport using a new camera separate from our gamecam
        //define our stage using that viewport and our games spritebatch
//        viewport = new FitViewport(Gdx.graphics.getWidth(), 50, new OrthographicCamera());
//        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), 50);
//        setViewport(viewport);


        //define a table used to organize our hud's labels
        final Table table = new Table();
        table.top();
        table.setFillParent(true);

        System.out.println("drw add btn");
        final Button drawMaze = new ImageButton(AtlasHelper.INSTANCE.createSpriteDrawable("draw-maze-btn"));
        drawMaze.addListener(new DrawMazeClickListener());
        table.add(drawMaze).pad(10);

//        final Button greedyBest = new ImageButton(ATLAS_HELPER.INSTANCE.createSpriteDrawable("greedy-best-btn"));
//        table.add(drawMaze).expandX().padTop(10);


        addActor(table);

    }

    public void update(float dt) {

    }

}