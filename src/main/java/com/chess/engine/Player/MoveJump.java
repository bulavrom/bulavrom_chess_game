package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Move;

public class MoveJump{

    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveJump(Board board, Move move, MoveStatus moveStatus) {
        this.board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }
}
