package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * The abstract class Tile
 */
public abstract class Tile {
    int tileCoordinate;

    /**
     * Standart constructor
     * @param tileCoordinate coordinate of Tile
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
