package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;


/**
 * This class represent the board after making move
 */
public class MoveJump{

    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;

    /**
     * Default constructor
     * @param board given Jump Board (board after executed move)
     * @param move given move
     * @param moveStatus given move status
     */
    public MoveJump(Board board, Move move, MoveStatus moveStatus) {
        this.board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    /**
     * Method that returns MoveStatus of Move Jump
     * it could be DONE,ILLEGAL_MOVE,GENERATE_CHECK
     * @return move status
     */
    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }


    /**
     * Method that returns actual Jump Board (Board after move)
     * @return board
     */
    public Board getBoard(){
        return this.board;
    }
}
