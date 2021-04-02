package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * OccupiedTile is Tile that is occupied by Piece, is extends abstract class Tile
 */
public final class OccupiedTile extends Tile{
    Piece pieceOnTile;

    /**
     * Standart Constructor
     * @param tileCoordinate coordinate of Tile
     * @param pieceOnTile piece on Tile
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
