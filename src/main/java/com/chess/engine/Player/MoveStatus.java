package com.chess.engine.Player;

public enum MoveStatus {
    DONE{
        @Override
        boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE{
        @Override
        boolean isDone() {
            return false;
        }
    },
    GENERATE_CHECK{
        @Override
        boolean isDone() {
            return false;
        }
    };

    abstract boolean isDone();
}
