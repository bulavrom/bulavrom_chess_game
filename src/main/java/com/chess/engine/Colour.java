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
        public String getPlayerNameColour() {
            return "white";
        }

        @Override
        public boolean isPieceOnEightRank(final int piecePosition) {
            return piecePosition >= 0 && piecePosition <=7;
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
        public String getPlayerNameColour() {
            return "black";
        }

        @Override
        public boolean isPieceOnEightRank(final int piecePosition) {
            return piecePosition >= 48 && piecePosition <= 55;
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

    /**
     * Method that controls if Colour is Black
     * @return true if black
     */
    public abstract boolean isBlack();
    /**
     * Method that controls if Colour is White
     * @return true if white
     */
    public abstract boolean isWhite();

    /**
     * Return colour in string(use to write colour of player)
     * @return colour (white or black)
     */
    public abstract String getPlayerNameColour();

    /**
     * Method that controls if Piece is on Eight Rank(use for Pawn Promotion)
     * @param piecePosition piece position
     * @return true if piece is on eight rank
     */
    public abstract boolean isPieceOnEightRank(final int piecePosition);

    /**
     * Method that chooses player
     * @param whitePlayer white player
     * @param blackPlayer black player
     * @return black player if black, white player if white
     */
    public abstract Player choosePlayer(final WhitePlayer whitePlayer,final BlackPlayer blackPlayer);
}
