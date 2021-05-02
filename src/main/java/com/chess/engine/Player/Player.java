package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Move;
import com.chess.engine.Pieces.King;

import java.util.Collection;

public abstract class  Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> playerLegalMoves;

    Player(final Board board,final Collection<Move> playerLegalMoves, final Collection<Move> opponentLegalMoves){
        this.board = board;
        this.playerKing = createKing();
        this.playerLegalMoves = playerLegalMoves;
    }

}
