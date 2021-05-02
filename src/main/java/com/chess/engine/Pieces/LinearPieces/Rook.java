package com.chess.engine.Pieces.LinearPieces;

import com.chess.engine.Board.*;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends LinearPiece{

    private static final int[] possibleMovesOffsets = {-8,-1,1,8};

    /**
     * Standart Constructor
     *
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour   Colour of Piece (Black or White)
     */
    public Rook(int piecePosition, Colour pieceColour) {

        super(PieceType.ROOK, piecePosition, pieceColour);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        return calculateLinearLegalMoves(board,possibleMovesOffsets);
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
        if (((piecePosition % 8) + 1 == 1) &&  (offsetCandidate == -1)) {
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) &&  (offsetCandidate == 1);
    }
}
