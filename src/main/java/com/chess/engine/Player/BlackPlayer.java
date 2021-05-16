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

/**
 * Class that represents chess Black Player
 */
public class BlackPlayer extends Player{

    /**
     * Default constructor for a Black Player
     * @param board
     * @param blackLegalMoves
     * @param whiteLegalMoves
     */
    public BlackPlayer(final Board board, final Collection<Move> blackLegalMoves, final Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }


    /**
     * Method that returns all Black Active Pieces
     * @return all black active pieces
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackActivePieces();
    }


    /**
     * Method that returns a Black Colour(for a Black Player)
     * @return Black Colour
     */
    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    /**
     * Method that returns opposite player for Black Player
     * @return white Player
     */
    @Override
    public Player getOpponentPlayer() {
        return this.board.getWhitePlayer();
    }


    /**
     * Method that calculates possible castle moves for a Player
     * @param playerLegalMoves this player legal moves
     * @param opponentLegalMoves opponent player legal moves
     * @return ArrayList of possible castle moves
     */
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,final Collection<Move> opponentLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.getPlayerKing().isFirstMove() && !this.isInCheck()){
            // King side castle for a black Player
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if (Player.calculateAttacksOnTile(5,opponentLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            // Queen side castle for a black Player
            if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if (Player.calculateAttacksOnTile(2,opponentLegalMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(3,opponentLegalMoves).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
        return kingCastles;
    }
}
