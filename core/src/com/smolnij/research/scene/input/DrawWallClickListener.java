package com.smolnij.research.scene.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smolnij.research.scene.MazeRenderer;

public class DrawWallClickListener extends ClickListener {

    private final MazeRenderer mazeRenderer;

    public DrawWallClickListener(final MazeRenderer mazeRenderer) {
        this.mazeRenderer = mazeRenderer;
    }

    @Override
    public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
        mazeRenderer.toDrawWallsState();
        return true;
    }
}