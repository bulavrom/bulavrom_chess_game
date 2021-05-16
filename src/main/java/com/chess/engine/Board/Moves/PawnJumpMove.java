package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Board.Builder;
import com.chess.engine.Pieces.Pawn;
import com.chess.engine.Pieces.Piece;

import java.awt.image.BufferedImage;

/**
 * Class that represent a pawn jump move
 */
public class PawnJumpMove extends Move{

    /**
     * Default constructor for a pawn jump move
     * @param board actual board
     * @param pieceToMove piece to move
     * @param destinationCoordinate destination coordinate
     */
    public PawnJumpMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }

    @Override
    public Board execute(){
        final Builder builder = new Builder();
        for (final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
            if (!(this.pieceToMove.equals(piece))){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece:this.board.getCurrentPlayer().getOpponentPlayer().getActivePieces()) {
            builder.setPiece(piece);
        }
        final Pawn pawnToJumpMove = (Pawn) this.pieceToMove.movePiece(this);
        builder.setPiece(pawnToJumpMove);
        builder.setEnPassantPawn(pawnToJumpMove);
        builder.setColourThatMovesNext(this.board.getCurrentPlayer().getOpponentPlayer().getColour());
        return builder.build();
    }

    @Override
    public String toString(){
        return board.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
