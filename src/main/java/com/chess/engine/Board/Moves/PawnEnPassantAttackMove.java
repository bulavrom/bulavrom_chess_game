package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Board.*;
import com.chess.engine.Pieces.Piece;

public class PawnEnPassantAttackMove extends PawnAttackMove{
    public PawnEnPassantAttackMove(Board board, Piece pawnToMove, int destinationCoordinate, Piece attackedPiece) {
        super(board, pawnToMove, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
    }

    @Override
    public Board execute(){
        final Builder builder = new Builder();
        for (final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.pieceToMove.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece:this.board.getCurrentPlayer().getOpponentPlayer().getActivePieces()) {
            if (!piece.equals(this.getAttackedPiece()))
            builder.setPiece(piece);
        }
        builder.setPiece(this.pieceToMove.movePiece(this));
        builder.setColourThatMovesNext(this.board.getCurrentPlayer().getOpponentPlayer().getColour());
        return builder.build();
    }

    @Override
    public boolean isEnPassantAttackMove(){
        return true;
    }
}
