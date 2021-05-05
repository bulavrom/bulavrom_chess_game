package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

/**
 *  Class for all moves
 */
public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final int destinationCoordinate;

    public static final Move INVALID_MOVE = new InvaildMove();

    public Move(final Board board, final Piece pieceToMove, final int destinationCoordinate){
        this.board = board;
        this.pieceToMove = pieceToMove;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    public Piece getPieceToMove(){
        return this.pieceToMove;
    }

    public Board execute(){
        final Board.Builder builder = new Board.Builder();

        for (final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.pieceToMove.equals(piece)){
                builder.setPiece(piece);
            }
        }

        for (final Piece piece : this.board.getCurrentPlayer().getOpponentPlayer().getActivePieces()) {
            builder.setPiece(piece);
        }

        builder.setPiece(this.pieceToMove.movePiece(this));
        builder.setColourThatMovesNext(this.board.getCurrentPlayer().getOpponentPlayer().getColour());
        return builder.build();
    }

    public int getCurrentCoordinate(){
        return this.pieceToMove.getPiecePosition();
    }

    public static class MoveFactory {
        private MoveFactory(){
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board, final int currentCoordinate,final int destinationCoordinate){
            for (final Move move: board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return INVALID_MOVE;
        }
    }
}
