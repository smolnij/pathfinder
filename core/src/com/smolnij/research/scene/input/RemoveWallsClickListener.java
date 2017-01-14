package com.smolnij.research.scene.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smolnij.research.scene.MazeRenderer;

public class RemoveWallsClickListener extends ClickListener {
    private final MazeRenderer mazeRenderer;

    public RemoveWallsClickListener(MazeRenderer mazeRenderer) {
        this.mazeRenderer = mazeRenderer;
    }

    @Override
    public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
        mazeRenderer.toRemoveWallsState();
        return true;
    }
}