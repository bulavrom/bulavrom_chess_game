package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Board.Builder;
import com.chess.engine.Pieces.Piece;

/**
 *  Abstract class for all moves
 */
public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final int destinationCoordinate;
    final boolean isFirstMove;


    /**
     * Default constructor for move
     * @param board board
     * @param pieceToMove piece to move
     * @param destinationCoordinate destination coordinate
     */
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

    /**
     * Getter for a destination coordinate
     * @return destination coordinate
     */
    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    /**
     * Method that detect if it is  attack move
     * @return true if attack move
     */
    public boolean isAttackMove(){
        return false;
    }

    /**
     * Method that detect if it is castling move
     * @return true if move is castle
     */
    public boolean isCastlingMove(){
        return false;
    }

    /**
     * Getter for an attacked piece
     * @return null (attacked piece can be obly in attack move class)
     */
    public Piece getAttackedPiece(){
        return null;
    }

    /**
     * Getter for a board
     * @return board
     */
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


    /**
     * Getter for a piece to move
     * @return piece to move
     */
    public Piece getPieceToMove(){
        return this.pieceToMove;
    }

    /**
     * Methode that executes the move
     * @return new board after move
     */
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

    /**
     * Getter for a current piece coordinate
     * @return start piece position
     */
    public int getCurrentCoordinate(){
        return this.pieceToMove.getPiecePosition();
    }

    /**
     * Class that is used to create a new move
     */
    public static class MoveFactory {

        public static final Move INVALID_MOVE = new InvaildMove();

        /**
         * This class isnt instatible
         */
        private MoveFactory(){
            throw new RuntimeException("Not instantiable");
        }

        /**
         * Method that creates a move for a given board , current piece coordinate and destination coordinate
         * @param board board
         * @param currentCoordinate current piece coordinate
         * @param destinationCoordinate destination coordinate
         * @return created move if it is legal move, else returns invalid move
         */
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
