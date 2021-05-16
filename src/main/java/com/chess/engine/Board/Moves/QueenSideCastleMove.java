package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.LinearPieces.Rook;
import com.chess.engine.Pieces.Piece;

/**
 * Class that represent a Queen side castle move
 */
public class QueenSideCastleMove extends CastleMove{
    public QueenSideCastleMove(Board board, Piece pieceToMove, int destinationCoordinate,final Rook castleRook,
                              final int castleRookStartCoordinate, final int castleRookDestinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate,castleRook,castleRookStartCoordinate,castleRookDestinationCoordinate);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof QueenSideCastleMove && super.equals(other);
    }

    @Override
    public String toString(){
        return "O-O-O";
    }
}
