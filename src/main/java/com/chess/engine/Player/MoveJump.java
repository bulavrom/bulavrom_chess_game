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

    public MoveJump(Board board, Move move, MoveStatus moveStatus) {
        this.board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }
}
