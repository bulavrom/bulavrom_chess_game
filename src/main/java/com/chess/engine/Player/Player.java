package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.King;
import com.chess.engine.Pieces.Piece;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class  Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> playerLegalMoves;
    private final boolean isInCheck;

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
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegalMoves).isEmpty();
    }


    /**
     * Method that calculates all Attack Moves for a Tile (Piece on Tile)
     * @param piecePosition coordinate of Piece (also Tile Coordinate)
     * @param moves moves
     * @return List of Moves having piecePosition as a distination coordinate
     */
    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackingMoves = new ArrayList<>();
        for (final Move move: moves) {
            if(piecePosition == move.getDestinationCoordinate()){
                attackingMoves.add(move);
            }
        }
        return attackingMoves;
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
        return this.isInCheck;
    }

    /**
     * Method that controls if Player is in Check Mate (Lose)
     * @return true in case of Check Mate
     */
    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves(){
        for (final Move move:this.playerLegalMoves){
            final MoveJump moveJump = makeMove(move);
            if(moveJump.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that controls if Player is in Stale Mate (have no pieces or there arent any legal moves)
     * @return true in case of Stale Mate
     */
    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }


    // need to be implemented
    public boolean isCastled(){
        return false;
    }

    public King getPlayerKing(){
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves(){
        return this.playerLegalMoves;
    }

    public MoveJump makeMove(final Move move){

        if (!isThatMoveLegal(move)){
            return new MoveJump(this.board,move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board jumpBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(jumpBoard.getCurrentPlayer().getOpponentPlayer().getPlayerKing().getPiecePosition(),
                jumpBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()){
            return new MoveJump(this.board, move,MoveStatus.GENERATE_CHECK);
        }


        return new MoveJump(jumpBoard,move,MoveStatus.DONE);
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
