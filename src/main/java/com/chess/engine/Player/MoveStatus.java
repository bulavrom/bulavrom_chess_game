package com.chess.engine.Player;

public enum MoveStatus {
    DONE{
        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public boolean isCheckMove() {
            return false;
        }
    },
    ILLEGAL_MOVE{
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public boolean isCheckMove() {
            return false;
        }
    },
    GENERATE_CHECK{
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public boolean isCheckMove() {
            return true;
        }
    };


    public abstract boolean isDone();
    public abstract boolean isCheckMove();
}
