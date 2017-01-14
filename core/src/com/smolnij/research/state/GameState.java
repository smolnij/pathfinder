package com.smolnij.research.state;

public enum GameState {
    INSTANCE;

    private State currentState = State.WAIT_FOR_INPUT;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentStateTo(final State state) {
        currentState = state;
    }

    public boolean isCurrentStateDrawWalls() {
        return currentState == State.DRAW_WALLS;
    }

    public boolean isCurrentStateRemoveWalls() {
        return currentState == State.REMOVE_WALLS;
    }

    public enum State {
        WAIT_FOR_INPUT, DRAW_WALLS, REMOVE_WALLS
    }
}
