package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

/**
 * Class that represent a standart move
 */
public final class StandartMove extends Move {

    /**
     * Default constructor for a standart move
     * @param board
     * @param pieceToMove
     * @param destinationCoordinate
     */
    public StandartMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof StandartMove && super.equals(other);
    }

    @Override
    public String toString(){
        return pieceToMove.getPieceType().toString() + board.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
