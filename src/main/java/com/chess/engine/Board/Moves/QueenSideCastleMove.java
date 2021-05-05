package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class QueenSideCastleMove extends CastleMove{
    public QueenSideCastleMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }
}
