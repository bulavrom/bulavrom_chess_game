package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

/**
 * OccupiedTile is Tile that is occupied by Piece, is extends abstract class Tile
 */
public final class OccupiedTile extends Tile{
    Piece pieceOnTile;

    /**
     * Standart constructor of Occupied Tile
     * @param tileCoordinate coordinate of tile
     * @param pieceOnTile piece on tile
     */
    public OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
        super(tileCoordinate);
        this.pieceOnTile = pieceOnTile;
    }

    /**
     * Overrided that return true because Tile is Occupied by Piece
     * @return true
     */
    @Override
    public boolean isTileOccupied(){
        return true;
    }

    /**
     * Method that returns piece on Tile
     * @return Piece
     */
    @Override
    public Piece getPiece(){
        return this.pieceOnTile;
    }
}
