import com.chess.engine.Board.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void initializeBoard(){
        final Board board = Board.createDefaultBoard();
        assertEquals(board.getCurrentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.getCurrentPlayer().getOpponentPlayer().getLegalMoves().size(), 20);
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().isCastled());
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrentPlayer().getOpponentPlayer(), board.getBlackPlayer());
        assertFalse(board.getCurrentPlayer().getOpponentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().getOpponentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().getOpponentPlayer().isCastled());
    }

}