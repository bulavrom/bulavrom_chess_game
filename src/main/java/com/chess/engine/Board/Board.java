package com.chess.engine.Board;

import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Colour;
import com.chess.engine.Pieces.*;
import com.chess.engine.Pieces.LinearPieces.*;
import com.chess.engine.Player.BlackPlayer;
import com.chess.engine.Player.Player;
import com.chess.engine.Player.WhitePlayer;
import com.chess.gui.ChessGUI.*;

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
    private final Player currentPlayer;

    private final Pawn enPassantPawn;


    /**
     * Standart constructor of Board
     * @param builder Builder of Board
     */
    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = createListOfPieces(this.gameBoard, Colour.WHITE);
        this.blackPieces = createListOfPieces(this.gameBoard, Colour.BLACK);
        this.enPassantPawn = builder.enPassantPawn;

        final Collection<Move> whiteLegalMoves = calculateColourLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = calculateColourLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, blackLegalMoves, whiteLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackLegalMoves, whiteLegalMoves);
        this.currentPlayer = builder.colourThatMovesNext.choosePlayer(this.whitePlayer, this.blackPlayer);
    }


    /**
     * Method that calculates and returns legal moves for all given pieces
     * @param pieces pieces
     * @return all legal moves for that pieces
     */
    private Collection<Move> calculateColourLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece:pieces) {
//            legalMoves.add((Move) piece.calculateLegalMoves(this));
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return legalMoves;
    }


    /**
     * Method that returns black player
     * @return black player
     */
    public Player getBlackPlayer(){
        return this.blackPlayer;
    }

    /**
     * Method that returns white player
     * @return white player
     */
    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    public Collection<Piece> getBlackActivePieces(){
        return this.blackPieces;
    }


    /**
     * Method that returns list of White pieces that are on the Board
     * @return list of white pieces
     */
    public Collection<Piece> getWhiteActivePieces(){
        return this.whitePieces;
    }


    /**
     * Method that creates List Of Pieces for a given colour
     * @param gameBoard actual Game Board
     * @param colour colour of Pieces
     * @return list of Pieces
     */
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

    /**
     * Method that returns tile
     * @param tileCoordinate 1 - dimensional coordinate of Tile
     * @return tile that has tileCoordinate
     */
    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }


    public Pawn getEnPassantPawn(){
        return this.enPassantPawn;
    }

    /**
     * Method that creates Game Board with 64 Tiles
     * @param builder Builder of Board
     * @return list of Board's Tiles
     */
    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[64];
        for (int i = 0; i < 64; i++) {
            tiles[i] = Tile.createTile(i,builder.boardConfig.get(i));
        }
        List<Tile> listOfTiles = new ArrayList<>(Arrays.asList(tiles));
        return listOfTiles;
    }

    /**
     * Method that creates default Board with all pieces in default positions
     * @return default board
     */
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

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public Collection<Move> getAllLegalMoves() {
        List<Move> allLegalMoves = new ArrayList<>(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return allLegalMoves;
    }

    /**
     * Builder for Board
     */
    public static class Builder{

        Map<Integer, Piece> boardConfig = new HashMap<>();
        Colour colourThatMovesNext;
        Pawn enPassantPawn;

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

        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }

    public enum BoardDirection{
        NORMAL{
            @Override
            public List<ChessTilePanel> reverse(List<ChessTilePanel> tilesOnBoard) {
                return tilesOnBoard;
            }

            @Override
            public BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            public List<ChessTilePanel> reverse(List<ChessTilePanel> tilesOnBoard) {
                List<ChessTilePanel> reversed;
                reversed = tilesOnBoard;
                Collections.reverse(reversed);
                return reversed;
            }

            @Override
            public BoardDirection opposite() {
                return NORMAL;
            }
        };

        public abstract List<ChessTilePanel> reverse(final List<ChessTilePanel> tilesOnBoard);
        public abstract BoardDirection opposite();
    }
}
