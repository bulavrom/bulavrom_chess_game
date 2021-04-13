package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

/**
 * The abstract class Tile
 */
public abstract class Tile {
    int tileCoordinateX;
    int tileCoordinateY;


    /**
     * Standart Constructor
     * @param tileCoordinateX
     * @param tileCoordinateY
     */
    Tile(int tileCoordinateX, int tileCoordinateY){
        this.tileCoordinateX = tileCoordinateX;
        this.tileCoordinateY = tileCoordinateY;
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
