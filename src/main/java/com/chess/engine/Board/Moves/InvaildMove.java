package com.chess.engine.Board.Moves;

import com.chess.engine.Board.Board;
import com.chess.engine.Pieces.Piece;

public class InvaildMove extends Move{

    public InvaildMove() {
        super(null, -1);
    }

    @Override
    public Board execute(){
        throw new RuntimeException("Cannot execute invalid move");
    }

    @Override
    public int getCurrentCoordinate(){
        return -1;
    }


}
