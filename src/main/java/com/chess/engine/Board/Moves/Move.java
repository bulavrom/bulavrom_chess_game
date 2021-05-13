package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Board.Builder;
import com.chess.engine.Pieces.Piece;

/**
 *  Class for all moves
 */
public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final int destinationCoordinate;
    final boolean isFirstMove;



    public Move(final Board board, final Piece pieceToMove, final int destinationCoordinate){
        this.board = board;
        this.pieceToMove = pieceToMove;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = pieceToMove.isFirstMove();
    }

    public Move(final Board board,final int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.pieceToMove = null;
        this.isFirstMove = false;
    }

    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    public boolean isAttackMove(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    public Board getBoard(){
        return this.board;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.pieceToMove.hashCode();
        result = prime * result + this.pieceToMove.getPiecePosition();

        return result;
    }

    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move)other;
        return  getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getPieceToMove().equals(otherMove.getPieceToMove());

    }


    public Piece getPieceToMove(){
        return this.pieceToMove;
    }

    public Board execute(){
        final Builder builder = new Builder();

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

        public static final Move INVALID_MOVE = new InvaildMove();

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
