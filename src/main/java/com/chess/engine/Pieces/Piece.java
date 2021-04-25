package com.chess.engine.Pieces;

import com.chess.engine.Board.Move;
import com.chess.engine.Board.OccupiedTile;
import com.chess.engine.Board.Tile;
import com.chess.engine.Colour;
import com.chess.engine.Board.Board;

import java.util.Collection;
import java.util.List;

/**
 * Abstract class Piece ,from this class will be extended all Pieces
 */
public abstract class Piece {
    protected final int piecePosition;
    protected final Colour pieceColour;

    /**
     * Standart Constructor
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour Colour of Piece (Black or White)
     */
    public Piece(int piecePosition, Colour pieceColour) {
        this.piecePosition = piecePosition;
        this.pieceColour = pieceColour;
    }

    /**
     * Method that will be overrided in extended classes to calculate all Legal Moves for the Piece
     * @param board board
     * @return returns List of Moves
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Colour getPieceColour(){
        return this.pieceColour;
    }
}
