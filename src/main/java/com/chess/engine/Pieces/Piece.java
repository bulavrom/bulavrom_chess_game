package com.chess.engine.Pieces;

import com.chess.engine.Board.Tile;
import com.chess.engine.Colour;
import com.chess.engine.Move;
import com.chess.engine.Board.Board;

import java.util.List;

/**
 * Abstract class Piece ,from this class will be extended all Pieces
 */
public abstract class Piece {
    protected final Tile tile;
    protected final Colour pieceColour;

    /**
     * Standart constructor
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour Colour of Piece(Black or White)
     */
    public Piece(int tileCoordinateX, int tileCoordinateY, Colour pieceColour) {
        this.tile = getTile(tileCoordinateX,tileCoordinateY);
        this.pieceColour = pieceColour;
    }

    /**
     * Method that will be overrided in extended classes to calculate all Legal Moves for the Piece
     * @param board board
     * @return returns List of Moves
     */
    public abstract List<Move> calculateLegalMoves(final Board board);
}
