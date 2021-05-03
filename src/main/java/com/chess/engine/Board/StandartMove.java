package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

public final class StandartMove extends Move {

    public StandartMove(Board board, Piece pieceToMove, int destinationCoordinate) {
        super(board, pieceToMove, destinationCoordinate);
    }


    /**
     * Method that makes a move
     * it creates a new board (with builder) with all pieces from standart board except piece that is going to move
     * @return board after executed move
     */
    @Override
    public Board execute() {
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
}
