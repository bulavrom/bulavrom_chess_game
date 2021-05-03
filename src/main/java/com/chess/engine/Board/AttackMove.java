package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

public final class AttackMove extends Move {

    final Piece attackedPiece;

    public AttackMove(Board board, Piece pieceToMove, int destinationCoordinate,Piece attackedPiece) {
        super(board, pieceToMove, destinationCoordinate);
        this.attackedPiece = attackedPiece;
    }

    @Override
    public Board execute() {
        return null;
    }
}
