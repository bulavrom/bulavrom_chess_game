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
     * @return
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
     * Standart constructor
     * @param tileCoordinate
     */
    Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
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
