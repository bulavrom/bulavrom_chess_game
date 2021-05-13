package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class StandartAttackMove extends AttackMove {
     public StandartAttackMove(final Board board, final Piece pieceToMove, final int destinationCoordinate, final Piece attackedPiece){
         super(board,pieceToMove,destinationCoordinate,attackedPiece);
     }

     @Override
    public boolean equals(final Object other){
         return this == other || other instanceof StandartAttackMove && super.equals(other);
     }
}
