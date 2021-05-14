package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Pawn;
import com.chess.engine.Pieces.Piece;

public class PawnPromotionMove extends Move {

    final Move pawnPromotionMove;
    final Pawn promotedPawn;

    public PawnPromotionMove(final Move pawnPromotionMove) {
        super(pawnPromotionMove.getBoard(), pawnPromotionMove.getPieceToMove(), pawnPromotionMove.getDestinationCoordinate());
        this.pawnPromotionMove = pawnPromotionMove;
        this.promotedPawn = (Pawn) pawnPromotionMove.getPieceToMove();
    }

    @Override
    public Board execute(){
        final Board movedPawnBoard = this.pawnPromotionMove.execute();
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : movedPawnBoard.getCurrentPlayer().getActivePieces()) {
            if (!this.promotedPawn.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece:movedPawnBoard.getCurrentPlayer().getOpponentPlayer().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
        builder.setColourThatMovesNext(this.board.getCurrentPlayer().getOpponentPlayer().getColour());
        return builder.build();
    }

    @Override
    public boolean isAttackMove(){
        return this.pawnPromotionMove.isAttackMove();
    }

    @Override
    public Piece getAttackedPiece(){
        return this.pawnPromotionMove.getAttackedPiece();
    }

    @Override
    public int hashCode(){
        return this.pawnPromotionMove.hashCode() + (31 * this.promotedPawn.hashCode());
    }

    @Override
    public boolean equals(Object other){
        return this == other || other instanceof PawnPromotionMove && super.equals(other);
    }
}
