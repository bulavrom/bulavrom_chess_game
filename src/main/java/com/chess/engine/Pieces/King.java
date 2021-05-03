package com.chess.engine.Pieces;

import com.chess.engine.Board.*;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.LinearPieces.Bishop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{

    private static final int[] possibleMovesOffsets = {-9,-8,-7,-1,1,7,8,9};
    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public King(final int piecePosition,final Colour pieceColour) {

        super(PieceType.KING, piecePosition, pieceColour);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(),move.getPieceToMove().getPieceColour());
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        int possibleMoveDestinationCoordinate;

        for (final int offsetCandidate: possibleMovesOffsets) {
            possibleMoveDestinationCoordinate = this.piecePosition + offsetCandidate;

            if(possibleMoveDestinationCoordinate >= 0  && possibleMoveDestinationCoordinate < 64){
                if(coloumnExclusions(this.piecePosition, offsetCandidate)){
                    continue;
                }
                final Tile tileCandidate  = board.getTile(possibleMoveDestinationCoordinate);
                if(tileCandidate.isTileOccupied()){
                    final Piece pieceOnCandidateTile = tileCandidate.getPiece();
                    final Colour candidatePieceColour = pieceOnCandidateTile.getPieceColour();
                    if (candidatePieceColour != this.pieceColour){
                        legalMoves.add(new AttackMove(board,this, possibleMoveDestinationCoordinate, pieceOnCandidateTile));
                    }
                }
                else {
                    legalMoves.add(new StandartMove(board,this,possibleMoveDestinationCoordinate));
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
        if (((piecePosition % 8) + 1 == 1) && ((offsetCandidate == -9) || (offsetCandidate == -1) ||
                (offsetCandidate == 7))){
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) && ((offsetCandidate == -7) || (offsetCandidate == 1) ||
                (offsetCandidate == 9));
    }
}
