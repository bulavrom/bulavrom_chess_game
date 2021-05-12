package com.chess.engine.Player;

public enum MoveStatus {
    DONE{
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE{
        @Override
        public boolean isDone() {
            return false;
        }
    },
    GENERATE_CHECK{
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();
}
