package com.chess.engine.Pieces;

import com.chess.engine.Board.*;
import com.chess.engine.Colour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece{


    private static final int[] possibleMovesOffsets = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public Knight(final int piecePosition, final Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        int possibleMoveDestinitionCoordinate;

        for (final int offsetCandidate: possibleMovesOffsets) {
            possibleMoveDestinitionCoordinate = this.piecePosition + offsetCandidate;

            if(possibleMoveDestinitionCoordinate >= 0  && possibleMoveDestinitionCoordinate < 64){
                if(coloumnExclusions(this.piecePosition, offsetCandidate)){
                    continue;
                }
                final Tile tileCandidate  = board.getTile(possibleMoveDestinitionCoordinate);
                if(tileCandidate.isTileOccupied()){
                    final Piece pieceOnCandidateTile = tileCandidate.getPiece();
                    final Colour candidatePieceColour = pieceOnCandidateTile.getPieceColour();
                    if (candidatePieceColour != this.pieceColour){
                        legalMoves.add(new AttackMove(board,this, possibleMoveDestinitionCoordinate, pieceOnCandidateTile));
                    }
                }
                else {
                    legalMoves.add(new StandartMove(board,this,possibleMoveDestinitionCoordinate));
                }

            }
        }
        return legalMoves;
    }

    /**
     * Some offsets doesnt work correctly in 1,2,7,8 coloumns , this method detect these exclusions
     * @param piecePosition pieceCoordination (is needed to detect coloumn)
     * @param offsetCandidate offset to control
     * @return true in case of exclusion
     */
    private static boolean coloumnExclusions(int piecePosition, int offsetCandidate){

        //first coloumn exclusion
        if (((piecePosition % 8) + 1 == 1) && ((offsetCandidate == -17) || (offsetCandidate == -10) ||
                (offsetCandidate == 6) || (offsetCandidate == 15))){
            return true;
        }

        //second coloumn exclusion
        if (((piecePosition % 8) + 1 == 2) && ((offsetCandidate == -10) || (offsetCandidate == 6))){
            return true;
        }

        //seventh coloumn exclusion
        if (((piecePosition % 8) + 1 == 7) && ((offsetCandidate == -6) || (offsetCandidate == 10))){
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) && ((offsetCandidate == -15) || (offsetCandidate == -6) ||
                (offsetCandidate == 10) || (offsetCandidate == 17));
    }
}