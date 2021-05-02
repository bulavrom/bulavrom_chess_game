package com.chess.engine.Pieces;

import com.chess.engine.Board.AttackMove;
import com.chess.engine.Board.Move;
import com.chess.engine.Board.StandartMove;
import com.chess.engine.Colour;
import com.chess.engine.Board.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    private static final int[] possibleMovesOffsets = {8,16};

    /**
     * Standart constructor
     *
     * @param piecePosition position of Tile where Piece is located
     * @param pieceColour   Colour of Piece(Black or White)
     */
    public Pawn(int piecePosition, Colour pieceColour) {

        super(PieceType.PAWN, piecePosition, pieceColour);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        int possibleMoveDestinationCoordinate;
        final int direction;

        if (this.getPieceColour() == Colour.BLACK){
            direction = 1;
        }else {
            direction = -1;
        }

        for (final int offsetCandidate: possibleMovesOffsets) {
            possibleMoveDestinationCoordinate = this.piecePosition + direction * offsetCandidate;

            if (!(possibleMoveDestinationCoordinate >= 0 && possibleMoveDestinationCoordinate < 64)){
                continue;
            }

            if (offsetCandidate == 8 && !board.getTile(possibleMoveDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new StandartMove(board, this, possibleMoveDestinationCoordinate));
            }else if(offsetCandidate == 16 && this.isFirstMove() &&
            isPawnOnStartPositionForBlack(this.piecePosition,this) ||
            isPawnOnStartPositionForWhite(this.piecePosition,this)){
                final int standartMoveDestinationCoordinate = this.piecePosition + direction * 8;
                if (!board.getTile(standartMoveDestinationCoordinate).isTileOccupied() &&
                    !board.getTile(possibleMoveDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new StandartMove(board, this, possibleMoveDestinationCoordinate));
                }
            }else if (!coloumnAttackMoveExclusion(this.piecePosition,offsetCandidate,this)){
                final Piece pieceOnCandidateTile = board.getTile(possibleMoveDestinationCoordinate).getPiece();
                if (this.getPieceColour() != pieceOnCandidateTile.getPieceColour()){
                    legalMoves.add(new AttackMove(board,this,possibleMoveDestinationCoordinate,pieceOnCandidateTile));
                }
            }

        }

        return legalMoves;
    }

    private static boolean isPawnOnStartPositionForBlack(int piecePosition, Piece piece){
        return piecePosition >= 8 && piecePosition <=15 && piece.getPieceColour().isBlack();
    }

    private static boolean isPawnOnStartPositionForWhite(int piecePosition, Piece piece){
        return piecePosition >= 48 && piecePosition <=55 && piece.getPieceColour().isWhite();
    }

    private static boolean coloumnAttackMoveExclusion(int piecePosition, int offsetCandidate, Piece piece){
        if (offsetCandidate == 7 && (((piecePosition % 8) + 1 == 8 && piece.getPieceColour().isWhite()) ||
                                    ((piecePosition % 8) + 1 == 1 && piece.getPieceColour().isBlack()))){
            return true;
        }
        return (offsetCandidate == 9 && (((piecePosition % 8) + 1 == 1 && piece.getPieceColour().isWhite()) ||
                ((piecePosition % 8) + 1 == 8 && piece.getPieceColour().isBlack())));
    }
}
