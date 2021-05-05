package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public abstract class CastleMove extends Move{

    public CastleMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }
}
