package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public final class StandartMove extends Move {

    public StandartMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }


    /**
     * Method that makes a move
     * it creates a new board (with builder) with all pieces from standart board except piece that is going to move
     * @return board after executed move
     */

}
