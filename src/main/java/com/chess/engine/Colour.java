package com.chess.engine;

/**
 * Colour (White or Black)
 */
public enum Colour {
    WHITE{
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }
    },
    BLACK{
        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }
    };

    public abstract boolean isBlack();
    public abstract boolean isWhite();
}
