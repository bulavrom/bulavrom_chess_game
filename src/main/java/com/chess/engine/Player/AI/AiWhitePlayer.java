package com.chess.engine.Player.AI;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Player.WhitePlayer;

import java.util.Collection;

public class AiWhitePlayer extends WhitePlayer {
    public AiWhitePlayer(Board board, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }
}
