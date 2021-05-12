package com.chess.gui;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Board.Tile;
import com.chess.engine.Pieces.Piece;
import com.chess.engine.Player.MoveJump;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.engine.Board.Board.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class ChessGUI {

    private final JFrame chessFrame;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece movedPiece;
    private final ChessBoardPanel chessBoardPanel;
    private boolean legalMovesHighlighted;
    private BoardDirection boardDirection;
    private final String pieceImagePath = "icons/pieces/";


    /**
     * Standard constructor that builds GUI
     */
    public ChessGUI(){
        this.chessFrame = new JFrame("Chess Game");
        this.chessFrame.setLayout(new BorderLayout());
        this.chessBoard = createDefaultBoard();
        this.chessBoardPanel = new ChessBoardPanel();
        this.legalMovesHighlighted = false;
        this.sourceTile = null;
        this.destinationTile = null;
        this.boardDirection = BoardDirection.NORMAL;


        final JMenuBar chessMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu boardMenu = new JMenu("Board");
        final JMenu settingsMenu = new JMenu();

        final JMenuItem openPGN = new JMenuItem("Open PGN file");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        final JMenuItem flipBoard = new JMenuItem("Change direction");
        flipBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                chessBoardPanel.drawBoard(chessBoard);
            }
        });
        boardMenu.add(flipBoard);

        boardMenu.addSeparator();
        final JCheckBoxMenuItem highlightLegalMovesCheckBox = new JCheckBoxMenuItem("Highlight legal moves",false);
        highlightLegalMovesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                legalMovesHighlighted = highlightLegalMovesCheckBox.isSelected();
            }
        });

        boardMenu.add(highlightLegalMovesCheckBox);
        fileMenu.add(openPGN);
        chessMenuBar.add(fileMenu);
        chessMenuBar.add(boardMenu);


        this.chessFrame.add(this.chessBoardPanel, BorderLayout.CENTER);

        this.chessFrame.setJMenuBar(chessMenuBar);


        this.chessFrame.setSize(new Dimension(800,800));
        this.chessFrame.setResizable(false);
        this.chessFrame.setVisible(true);
        this.chessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    /**
     * Class that represents Board in GUI as a class that extends from JPanel
     * contains 64 ChessTilePanels
     */
    private class ChessBoardPanel extends JPanel{
        final List<ChessTilePanel> tilesOnBoard;

        ChessBoardPanel(){
            super(new GridLayout(8,8));
            this.tilesOnBoard = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                ChessTilePanel chessTilePanel = new ChessTilePanel(this,i);
                this.tilesOnBoard.add(chessTilePanel);
                add(chessTilePanel);
            }
            setPreferredSize(new Dimension(500,500));
            validate();
        }

        public void drawBoard(final Board chessBoard) {
            removeAll();
            for (final ChessTilePanel tilePanel : boardDirection.reverse(tilesOnBoard)) {
                tilePanel.drawTile(chessBoard);
                add(tilePanel);
            }
            boardDirection = BoardDirection.NORMAL;
            validate();
            repaint();
        }


    }


    /**
     * Class that represents Tile in GUI as a class that extends from JPanel
     */
    public class ChessTilePanel extends JPanel{
        private final int tileId;

        /**
         * Constructor for a Tile panel
         * @param boardPanel
         * @param tileId
         */
        ChessTilePanel(final ChessBoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            this.setPreferredSize(new Dimension(10,10));

            if (((this.tileId + (this.tileId/8)) % 2) == 0){
                setBackground(Color.decode("#FFF08F"));
            }else{
                setBackground(Color.decode("#B57912"));
            }
            addPieceImage(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Right click to cancel
                    if (isRightMouseButton(e)){
                        //delete after debug
                        //System.out.println(chessBoard.getTile(tileId).getPiece().getPieceColour().toString() + chessBoard.getTile(tileId).getPiece().getPieceType().toString());
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;

                    }else if (isLeftMouseButton(e)){
                        //The first click
                        if(sourceTile == null){
                            sourceTile = chessBoard.getTile(tileId);
                            movedPiece = sourceTile.getPiece();
                            if(movedPiece == null){
                                sourceTile = null;
                            }
                        }else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard,sourceTile.getTileCoordinate(),destinationTile.getTileCoordinate());
                            final MoveJump moveJump = chessBoard.getCurrentPlayer().makeMove(move);
                            if(moveJump.getMoveStatus().isDone()){
                                chessBoard = moveJump.getBoard();
                            }
                            sourceTile = null;
                            destinationTile = null;
                            movedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            validate();
        }

        /**
         * Method that adds a Image Icon to the Tile there is the Piece placed
         * @param board given board
         */
        private void addPieceImage(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isTileOccupied()){
                try {
                    final BufferedImage imagePiece = ImageIO.read(new File(pieceImagePath + board.getTile(this.tileId).getPiece().getPieceColour().toString() +
                            board.getTile(this.tileId).getPiece().getPieceType().toString() + ".png"));
                    add(new JLabel(new ImageIcon(imagePiece)));
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

        /**
         * Method that draws a tile
         * @param board given board
         */
        public void drawTile (final Board board){
            if (((this.tileId + (this.tileId/8)) % 2) == 0){
                setBackground(Color.decode("#FFF08F"));
            }else{
                setBackground(Color.decode("#B57912"));
            }
            addPieceImage(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }

        private void highlightLegalMoves(final Board board){
            if (legalMovesHighlighted){
                for (final Move move : pieceLegalMoves(board)){
                    if (move.getDestinationCoordinate() == this.tileId){
                        try{
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("icons/icns/greencircle.png")))));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (movedPiece != null && movedPiece.getPieceColour() == board.getCurrentPlayer().getColour()){
                return movedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

    }
}
