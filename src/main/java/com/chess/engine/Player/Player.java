package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.King;
import com.chess.engine.Pieces.Piece;

import java.util.Collection;

public abstract class  Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> playerLegalMoves;

    /**
     * Standart Constructor
     * @param board
     * @param playerLegalMoves
     * @param opponentLegalMoves
     */
    Player(final Board board,final Collection<Move> playerLegalMoves, final Collection<Move> opponentLegalMoves){
        this.board = board;
        this.playerKing = createKing();
        this.playerLegalMoves = playerLegalMoves;
    }

    /**
     * Method that return King from the board
     * @return Piece King
     */
    private King createKing(){
        for (final Piece piece : getActivePieces()){
            if (piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Exception");
    }

    /**
     * Method that controls if move in list of Legal Moves
     * @param move move to control
     * @return true if move is in list of Legal Moves, else false
     */
    public boolean isThatMoveLegal(final Move move){
        return this.playerLegalMoves.contains(move);
    }

    /**
     * Method that controls if Player is in Check
     * @return true in case of Check
     */
    public boolean isInCheck(){
        return false;
    } // need to be implemented

    /**
     * Method that controls if Player is in Check Mate (Lose)
     * @return true in case of Check Mate
     */
    public boolean isInCheckMate(){
        return false;
    } // need to be implemented

    /**
     * Method that controls if Player is in Stale Mate (have no pieces or there arent any legal moves)
     * @return true in case of Stale Mate
     */
    public boolean isInStaleMate(){
        return false;
    } // need to be implemented


    // need to be implemented
    public boolean isCastled(){
        return false;
    }

    public MoveJump makeMove(final Move move){
        return null;
    }

    /**
     * Method that returns all player's Active Pieces
     * @return Collection of Active Pieces
     */
    public abstract Collection<Piece> getActivePieces();


    /**
     * Method that returns player's Colour
     * @return Black for BlackPlayer, White for WhitePlayer
     */
    public abstract Colour getColour();


    /**
     * Method that returns player's Opponent
     * @return BlackPlayer for WhitePlayer, WhitePlayer for BlackPlayer
     */
    public abstract Player getOpponentPlayer();
}
