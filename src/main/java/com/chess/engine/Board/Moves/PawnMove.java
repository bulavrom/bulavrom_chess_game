package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class PawnMove extends Move{
    public PawnMove(Board board, Piece pawnToMove, int destinationCoordinate) {
        super(board, pawnToMove, destinationCoordinate);
    }
}
