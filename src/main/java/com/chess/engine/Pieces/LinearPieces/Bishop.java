package com.chess.engine.Pieces.LinearPieces;

import com.chess.engine.Board.*;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.Piece;

import java.util.Collection;



public class Bishop extends LinearPiece {

    private static final int[] possibleMovesOffsets = {-9, -7, 7, 9};

    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public Bishop(final int piecePosition,final Colour pieceColour) {

        super(PieceType.BISHOP, piecePosition, pieceColour);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        return calculateLinearLegalMoves(board,possibleMovesOffsets);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Bishop(move.getDestinationCoordinate(),move.getPieceToMove().getPieceColour());
    }

    /**
     * Some offsets doesnt work correctly in 1,8 coloumns , this method detect these exclusions
     *
     * @param piecePosition   pieceCoordination (is needed to detect coloumn)
     * @param offsetCandidate offset to control
     * @return true in case of exclusion
     */
     @Override
     public boolean coloumnExclusions(int piecePosition, int offsetCandidate) {
        //first coloumn exclusion
        if (((piecePosition % 8) + 1 == 1) && ((offsetCandidate == -9) || (offsetCandidate == 7))) {
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) && ((offsetCandidate == -7) || (offsetCandidate == 9));
    }
}
