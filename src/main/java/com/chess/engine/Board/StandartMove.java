package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

public final class StandartMove extends Move {

    public StandartMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }
}
