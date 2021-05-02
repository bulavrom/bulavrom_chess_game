package com.chess.engine.Board;

import com.chess.engine.Colour;
import com.chess.engine.Pieces.*;
import com.chess.engine.Pieces.LinearPieces.*;
import com.chess.engine.Player.BlackPlayer;
import com.chess.engine.Player.WhitePlayer;

import java.util.*;


/**
 * Class of chess Board that will be contain 64 tiles
 */
public final class Board {

    private final List<Tile> gameBoard;

    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;


    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = createListOfPieces(this.gameBoard, Colour.WHITE);
        this.blackPieces = createListOfPieces(this.gameBoard, Colour.BLACK);

        final Collection<Move> whiteLegalMoves = calculateColourLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = calculateColourLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, blackLegalMoves, whiteLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackLegalMoves, whiteLegalMoves);
    }

    private Collection<Move> calculateColourLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece:pieces) {
            legalMoves.add((Move) piece.calculateLegalMoves(this));
        }
        return legalMoves;
    }

    private static Collection<Piece> createListOfPieces(List<Tile> gameBoard, Colour colour) {
        final List<Piece> pieceList = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if(tile.isTileOccupied()){
                if(tile.getPiece().getPieceColour() == colour){
                    pieceList.add(tile.getPiece());
                }
            }
        }
        return pieceList;
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[64];
        for (int i = 0; i < 64; i++) {
            tiles[i] = Tile.createTile(i,builder.boardConfig.get(i));
        }
        List<Tile> listOfTiles = new ArrayList<>(Arrays.asList(tiles));
        return listOfTiles;
    }

    public static Board createDefaultBoard(){
        final Builder builder = new Builder();
        // Set black pieces on start-game Positions
        builder.setPiece(new Pawn(8,Colour.BLACK));
        builder.setPiece(new Pawn(9,Colour.BLACK));
        builder.setPiece(new Pawn(10,Colour.BLACK));
        builder.setPiece(new Pawn(11,Colour.BLACK));
        builder.setPiece(new Pawn(12,Colour.BLACK));
        builder.setPiece(new Pawn(13,Colour.BLACK));
        builder.setPiece(new Pawn(14,Colour.BLACK));
        builder.setPiece(new Pawn(15,Colour.BLACK));
        builder.setPiece(new Rook(0,Colour.BLACK));
        builder.setPiece(new Knight(1,Colour.BLACK));
        builder.setPiece(new Bishop(2,Colour.BLACK));
        builder.setPiece(new Queen(3,Colour.BLACK));
        builder.setPiece(new King(4,Colour.BLACK));
        builder.setPiece(new Bishop(5,Colour.BLACK));
        builder.setPiece(new Knight(6,Colour.BLACK));
        builder.setPiece(new Rook(7,Colour.BLACK));

        // Set white pieces on start-game Positions
        builder.setPiece(new Pawn(48,Colour.WHITE));
        builder.setPiece(new Pawn(49,Colour.WHITE));
        builder.setPiece(new Pawn(50,Colour.WHITE));
        builder.setPiece(new Pawn(51,Colour.WHITE));
        builder.setPiece(new Pawn(52,Colour.WHITE));
        builder.setPiece(new Pawn(53,Colour.WHITE));
        builder.setPiece(new Pawn(54,Colour.WHITE));
        builder.setPiece(new Pawn(55,Colour.WHITE));
        builder.setPiece(new Rook(56,Colour.WHITE));
        builder.setPiece(new Knight(57,Colour.WHITE));
        builder.setPiece(new Bishop(58,Colour.WHITE));
        builder.setPiece(new Queen(59,Colour.WHITE));
        builder.setPiece(new King(60,Colour.WHITE));
        builder.setPiece(new Bishop(61,Colour.WHITE));
        builder.setPiece(new Knight(62,Colour.WHITE));
        builder.setPiece(new Rook(63,Colour.WHITE));

        // White moves first
        builder.setColourThatMovesNext(Colour.WHITE);
        return builder.build();
    }

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Colour colourThatMovesNext;

        public Builder(){
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setColourThatMovesNext(final Colour colourThatMovesNext){
            this.colourThatMovesNext = colourThatMovesNext;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

    }
}
