package com.chess.engine.Pieces;

import com.chess.engine.Board.*;
import com.chess.engine.Colour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece{

    private static final int[] possibleMovesOffsets = {-8,-1,1,8};

    /**
     * Standart Constructor
     *
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour   Colour of Piece (Black or White)
     */
    public Rook(int piecePosition, Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        int possibleMoveDestinationCoordinate;

        for (final int offsetCandidate : possibleMovesOffsets) {
            possibleMoveDestinationCoordinate = this.piecePosition;
            while (possibleMoveDestinationCoordinate >= 0 && possibleMoveDestinationCoordinate < 64) {
                if (coloumnExclusions(possibleMoveDestinationCoordinate, offsetCandidate)){
                    break;
                }
                possibleMoveDestinationCoordinate += offsetCandidate;
                if (possibleMoveDestinationCoordinate >= 0 && possibleMoveDestinationCoordinate < 64) {
                    final Tile tileCandidate = board.getTile(possibleMoveDestinationCoordinate);
                    if (tileCandidate.isTileOccupied()) {
                        final Piece pieceOnCandidateTile = tileCandidate.getPiece();
                        final Colour candidatePieceColour = pieceOnCandidateTile.getPieceColour();
                        if (candidatePieceColour != this.pieceColour) {
                            legalMoves.add(new AttackMove(board, this, possibleMoveDestinationCoordinate, pieceOnCandidateTile));
                        }
                        break;
                    } else {
                        legalMoves.add(new StandartMove(board, this, possibleMoveDestinationCoordinate));
                    }
                }
            }

        }
        return legalMoves;
    }

    /**
     * Some offsets doesnt work correctly in 1,8 coloumns , this method detect these exclusions
     *
     * @param piecePosition   pieceCoordination (is needed to detect coloumn)
     * @param offsetCandidate offset to control
     * @return true in case of exclusion
     */
    private static boolean coloumnExclusions(int piecePosition, int offsetCandidate) {
        //first coloumn exclusion
        if (((piecePosition % 8) + 1 == 1) &&  (offsetCandidate == -1)) {
            return true;
        }

        //eight coloumn exclusion
        return ((piecePosition % 8) + 1 == 8) &&  (offsetCandidate == 1);
    }
}
