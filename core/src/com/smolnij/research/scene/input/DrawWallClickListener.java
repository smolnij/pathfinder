package com.smolnij.research.scene.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smolnij.research.state.GameState;

public class DrawWallClickListener extends ClickListener {

    @Override
    public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
        GameState.INSTANCE.setCurrentStateTo(GameState.State.DRAW_MAZE);
        return true;
    }
}