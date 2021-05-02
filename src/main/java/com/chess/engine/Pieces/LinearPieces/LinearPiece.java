package com.chess.engine.Pieces.LinearPieces;

import com.chess.engine.Board.*;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class LinearPiece extends Piece {

    /**
     * Standart Constructor
     *
     * @param pieceType type of Piece
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour   Colour of Piece (Black or White)
     */
    public LinearPiece(PieceType pieceType,int piecePosition, Colour pieceColour) {

        super(pieceType, piecePosition, pieceColour);
    }

    /**
     * Pieces that moves linearly have simillar logic for calculate moves
     * @param board
     * @param possibleMovesOffsets
     * @return
     */
    public Collection<Move> calculateLinearLegalMoves(Board board, int[] possibleMovesOffsets){
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
     * Some offsets doesnt work correctly in some coloumns positions , this method detect these exclusions
     *
     * @param piecePosition   pieceCoordination (is needed to detect coloumn)
     * @param offsetCandidate offset to control
     * @return true in case of exclusion
     */
    public abstract boolean coloumnExclusions(int piecePosition, int offsetCandidate);


}
