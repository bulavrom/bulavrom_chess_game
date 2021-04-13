package com.chess.engine.Board;

import com.chess.engine.Pieces.Piece;

/**
 * Class extends Tile that is Empty
 */
public final class EmptyTile extends Tile{

    /**
     * Standart Constructor
     * @param coordinate coordinate of Tile
     */
    EmptyTile(int tileCoordinateX,int tileCoordinateY){
        super(tileCoordinateX, tileCoordinateY);
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
