package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

/**
 * Class extends Tile that is Empty
 */
public final class EmptyTile extends Tile{

    /**
     * Standart constructor
     * @param tileCoordinate tile coordinate
     */
    EmptyTile(int tileCoordinate){
        super(tileCoordinate);
    }

    /**
     * Overrided that return false because Tile is empty
     * @return false
     */
    @Override
    public boolean isTileOccupied(){
        return false;
    }

    /**
     * Overrided method that returns null because there is no Piece on empty Tile
     * @return null
     */
    @Override
    public Piece getPiece(){
        return null;
    }

}
