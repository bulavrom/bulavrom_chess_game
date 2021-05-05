package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class PawnEnPassantAttackMove extends PawnAttackMove{
    public PawnEnPassantAttackMove(Board board, Piece pawnToMove, int destinationCoordinate, Piece attackedPiece) {
        super(board, pawnToMove, destinationCoordinate, attackedPiece);
    }
}
