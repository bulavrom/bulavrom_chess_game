package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> blackLegalMoves, final Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhiteActivePieces();
    }

    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    @Override
    public Player getOpponentPlayer() {
        return this.board.getWhitePlayer();
    }
}
