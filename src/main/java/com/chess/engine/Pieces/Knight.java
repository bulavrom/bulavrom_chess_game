package com.chess.engine.Pieces;

import com.chess.engine.Board.*;
import com.chess.engine.Board.Moves.AttackMove;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Board.Moves.StandartMove;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.LinearPieces.Bishop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece{


    private static final int[] possibleMovesOffsets = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     * Default constructor
     *
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour Colour of Piece (Black or White)
     */
    public Knight(final int piecePosition, final Colour pieceColour) {
        super(PieceType.KNIGHT, piecePosition, pieceColour,true);
    }

    /**
     * Overloaded constructor that also takes a boolean variable 'isFirstMove'
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour Colour of Piece(Black or White)
     * @param isFirstMove boolean variable that represents if piece has moved or not
     */
    public Knight(final int piecePosition, final Colour pieceColour,final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, pieceColour,isFirstMove);
    }


    @Override
    public Piece movePiece(final Move move) {
        return new Knight(move.getDestinationCoordinate(),move.getPieceToMove().getPieceColour());
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