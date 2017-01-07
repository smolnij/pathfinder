package com.smolnij.research.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smolnij.research.layout.ATLAS_HELPER;

public class ControlPanel implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;


    public ControlPanel(final SpriteBatch sb) {
        //setup the HUD viewport using a new camera separate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Gdx.graphics.getWidth(), 50, new OrthographicCamera());
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), 50);
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        final Table table = new Table();
        table.top();
        table.setFillParent(true);

        final Button drawMaze = new ImageButton(ATLAS_HELPER.INSTANCE.createSpriteDrawable("draw-maze-btn"));
        table.add(drawMaze).expandX().padTop(10);

        final Button greedyBest = new ImageButton(ATLAS_HELPER.INSTANCE.createSpriteDrawable("greedy-best-btn"));
        table.add(drawMaze).expandX().padTop(10);



        stage.addActor(table);

    }

    public void update(float dt) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}