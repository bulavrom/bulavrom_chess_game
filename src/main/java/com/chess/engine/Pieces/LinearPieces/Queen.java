package com.chess.engine.Pieces.LinearPieces;

import com.chess.engine.Board.*;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends LinearPiece{

    private static final int[] possibleMovesOffsets = {-9,-8,-7,-1,1,7,8,9};


    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public Queen(final int piecePosition,final Colour pieceColour) {
        super(PieceType.QUEEN, piecePosition, pieceColour,true);
    }

    /**
     * Overloaded constructor that also takes a boolean variable 'isFirstMove'
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour Colour of Piece(Black or White)
     * @param isFirstMove boolean variable that represents if piece has moved or not
     */
    public Queen(final int piecePosition,final Colour pieceColour, final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePosition, pieceColour,isFirstMove);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Queen(move.getDestinationCoordinate(),move.getPieceToMove().getPieceColour());
    }


    @Override
    public Collection<Move> calculateLegalMoves(Board board){
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
        if (((piecePosition % 8) + 1 == 1) && ((offsetCandidate == -9) || (offsetCandidate == 7) ||
                (offsetCandidate == -1))) {
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) && ((offsetCandidate == -7) || (offsetCandidate == 9) ||
                (offsetCandidate == 1));
    }
}
