package com.chess.engine.Player.AI;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Player.Player;

import java.util.Collection;

public abstract class AiPlayer extends Player {

    /**
     * Standart Constructor
     *
     * @param board
     * @param playerLegalMoves
     * @param opponentLegalMoves
     */
    public AiPlayer(Board board, Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {
        super(board, playerLegalMoves, opponentLegalMoves);
    }
}
