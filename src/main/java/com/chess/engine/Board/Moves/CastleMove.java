package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Board.Builder;
import com.chess.engine.Pieces.LinearPieces.Rook;
import com.chess.engine.Pieces.Piece;

public abstract class CastleMove extends Move{

    protected final Rook castleRook;
    protected final int castleRookStartCoordinate;
    protected final int castleRookDestinationCoordinate;

    public CastleMove(final Board board,final Piece pieceToMove,final int destinationCoordinate,final Rook castleRook,
                      final int castleRookStartCoordinate, final int castleRookDestinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStartCoordinate = castleRookStartCoordinate;
        this.castleRookDestinationCoordinate = castleRookDestinationCoordinate;
    }

    public Rook getCastleRook() {
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove(){
        return true;
    }

    @Override
    public Board execute(){
        final Builder builder = new Builder();
        for (final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.pieceToMove.equals(piece) && !(this.castleRook.equals(piece))){
                builder.setPiece(piece);
            }
        }

        for (final Piece piece : this.board.getCurrentPlayer().getOpponentPlayer().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.pieceToMove.movePiece(this));
        builder.setPiece(new Rook(this.castleRookDestinationCoordinate, this.castleRook.getPieceColour()));
        builder.setColourThatMovesNext(this.board.getCurrentPlayer().getOpponentPlayer().getColour());
        return builder.build();
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.castleRook.hashCode();
        result = prime * result + this.castleRookDestinationCoordinate;
        return result;
    }
    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof CastleMove)){
            return false;
        }
        final CastleMove otherCastleMove = (CastleMove) other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }
}
