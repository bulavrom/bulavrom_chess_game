package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * The abstract class Tile
 */
public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer,EmptyTile> EMPTY_TILE_MAP = createEmptyTiles();

    /**
     * Method creates all empty tiles
     * @return Map of empty tiles
     */
    private static Map<Integer, EmptyTile> createEmptyTiles(){
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return emptyTileMap;
    }


    public static Tile createTile(final int tileCoordinate, final Piece piece){
        if(piece == null){
            return EMPTY_TILE_MAP.get(tileCoordinate);
        }
        else {
            return new OccupiedTile(tileCoordinate,piece);
        }
    }

    /**
     * Default tile constructor
     * @param tileCoordinate 1 dimensional tile coordinate
     */
    Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }


    /**
     * Getter for a tile coordinate
     * @return tile coordinate
     */
    public int getTileCoordinate(){
        return this.tileCoordinate;
    }


    /**
     * Method that return true if Tile is Occupied by Piece
     * @return false/true if Tile occupied
     */
    public abstract boolean isTileOccupied();

    /**
     * Method to return piece on Tile
     * @return piece
     */
    public abstract Piece getPiece();

}
