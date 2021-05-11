package com.chess.engine;

import com.chess.engine.Player.BlackPlayer;
import com.chess.engine.Player.Player;
import com.chess.engine.Player.WhitePlayer;

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

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString(){
            return "W";
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

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public String toString(){
            return "B";
        }
    };

    public abstract boolean isBlack();
    public abstract boolean isWhite();

    public abstract Player choosePlayer(final WhitePlayer whitePlayer,final BlackPlayer blackPlayer);
}
