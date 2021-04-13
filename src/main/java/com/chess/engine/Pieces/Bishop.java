package com.chess.engine.Pieces;

import com.chess.engine.Colour;
import com.chess.engine.Move;
import com.chess.engine.Board.Board;

import java.util.List;

public class Bishop extends Piece{
    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public Bishop(int piecePosition, Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        return null;
    }
}
