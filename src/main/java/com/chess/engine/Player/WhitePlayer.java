package com.chess.engine.Player;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.KingSideCastleMove;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Board.Moves.QueenSideCastleMove;
import com.chess.engine.Board.Tile;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.LinearPieces.Rook;
import com.chess.engine.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board, final Collection<Move> blackLegalMoves,final Collection<Move> whiteLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackActivePieces();
    }

    @Override
    public Colour getColour() {
        return Colour.WHITE;
    }

    @Override
    public Player getOpponentPlayer() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves, final Collection<Move> opponentLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.getPlayerKing().isFirstMove() && !this.isInCheck()){
            // King side castle for a white Player
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if (Player.calculateAttacksOnTile(61,opponentLegalMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(62, opponentLegalMoves).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            // Queen side castle for a white Player
            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
                !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if (Player.calculateAttacksOnTile(58,opponentLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }
        return kingCastles;
    }
}
