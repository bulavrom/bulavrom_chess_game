package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

/**
 *  Class for all moves
 */
public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final int destinationCoordinate;

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

    public abstract Board execute();
}
