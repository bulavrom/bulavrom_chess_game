package com.chess.engine.Pieces;

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
        super(piecePosition, pieceColour);
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
            }
            //dopisat  !!!
        }

        return legalMoves;
    }
}
