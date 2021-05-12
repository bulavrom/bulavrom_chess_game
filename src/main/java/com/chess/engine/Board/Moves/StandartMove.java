package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public final class StandartMove extends Move {

    public StandartMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof StandartMove && super.equals(other);
    }
}
