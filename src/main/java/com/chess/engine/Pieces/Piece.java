package com.chess.engine.Pieces;

import com.chess.engine.Board.Move;

import com.chess.engine.Colour;
import com.chess.engine.Board.Board;

import java.util.Collection;


/**
 * Abstract class Piece ,from this class will be extended all Pieces
 */
public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Colour pieceColour;
    protected final boolean isFirstMove;
    private final int cacheHashCode;

    /**
     * Standart Constructor
     *
     * @param pieceType Type Of Piece
     * @param piecePosition 1 Dimension coordinate on the board
     * @param pieceColour Colour of Piece (Black or White)
     */
    public Piece(final PieceType pieceType,final int piecePosition, final Colour pieceColour) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceColour = pieceColour;
        this.isFirstMove = false;
        this.cacheHashCode = calculateHashCode();
    }


    /**
     * Method that will be overrided in extended classes to calculate all Legal Moves for the Piece
     * @param board board
     * @return returns List of Moves
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);


    /**
     * Method that returns Piece Colour
     * @return Black if Piece is Black, White if Piece is White
     */
    public Colour getPieceColour(){
        return this.pieceColour;
    }


    /**
     * Method that controls if is the first move for a piece
     * @return true if its first move
     */
    protected boolean isFirstMove(){
        return this.isFirstMove;
    }


    /**
     * Method that returns 1 - dimensial Piece Position
     * @return Piece Position
     */
    public int getPiecePosition(){
        return this.piecePosition;
    }

    /**
     * Method that returns Piece Type (All piece types are in enum)
     * @return Piece Type
     */
    public PieceType getPieceType(){
        return this.pieceType;
    }


    /**
     * Ovverride of method "equals" to compare different pieces
     * @param other
     * @return
     */
    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceColour == otherPiece.getPieceColour() && isFirstMove == otherPiece.isFirstMove();
    }

    /**
     * Method that calculates idividual Hash Code
     * @return hash code for a piece
     */
    private int calculateHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceColour.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }


    /**
     * Overrited method that returns calculated and cached hash code
     * @return hash code
     */
    @Override
    public int hashCode(){
        return this.cacheHashCode;
    }

    public enum PieceType{
        PAWN{
            @Override
            public boolean isKing(){
                return false;
            }
        },
        KNIGHT {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        /**
         * Method that returns true if the Piece Type is King
         * @return true if the PieceType is King
         */
        public abstract boolean isKing();
    }
}
