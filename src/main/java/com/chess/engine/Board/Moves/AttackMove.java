package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class AttackMove extends Move {

    final Piece attackedPiece;

    public AttackMove(Board board, Piece pieceToMove, int destinationCoordinate, Piece attackedPiece) {
        super(board, pieceToMove, destinationCoordinate);
        this.attackedPiece = attackedPiece;
    }


    @Override
    public int hashCode(){
        return this.attackedPiece.hashCode() + super.hashCode();
    }

    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof AttackMove)){
            return false;
        }
        final AttackMove otherAttackMove = (AttackMove) other;
        return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
    }


    @Override
    public boolean isAttackMove(){
        return true;
    }

    @Override
    public Piece getAttackedPiece(){
        return this.attackedPiece;
    }
}
