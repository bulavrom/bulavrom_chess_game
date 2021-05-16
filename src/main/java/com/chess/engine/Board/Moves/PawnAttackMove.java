package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

/**
 * Class that represent a pawn attack move
 */
public class PawnAttackMove extends AttackMove{

    /**
     * Default constructor for a pawn attack move
     * @param board actual board
     * @param pawnToMove pawn to move
     * @param destinationCoordinate destination coordinate
     * @param attackedPiece attacked piece
     */
    public PawnAttackMove(Board board, Piece pawnToMove, int destinationCoordinate, Piece attackedPiece) {
        super(board, pawnToMove, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnAttackMove && super.equals(other);
    }

    @Override
    public String toString(){
        return board.getPositionAtCoordinate(this.pieceToMove.getPiecePosition()) + "x" +
                board.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
