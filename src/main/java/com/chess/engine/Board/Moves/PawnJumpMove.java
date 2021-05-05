package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class PawnJumpMove extends Move{
    public PawnJumpMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }
}
