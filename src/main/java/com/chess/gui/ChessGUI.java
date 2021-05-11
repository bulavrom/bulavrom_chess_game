package com.chess.gui;

import com.chess.engine.Board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessGUI {

    private final JFrame chessFrame;
    private final Board chessBoard;
    private final ChessBoardPanel chessBoardPanel;
    private final String pieceImagePath = "icons/pieces/";

    public ChessGUI(){
        this.chessFrame = new JFrame("Chess Game");
        this.chessFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createDefaultBoard();
        this.chessBoardPanel = new ChessBoardPanel();


        final JMenuBar chessMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu settingsMenu = new JMenu();

        final JMenuItem openPGN = new JMenuItem("Open PGN file");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        fileMenu.add(openPGN);
        chessMenuBar.add(fileMenu);

        this.chessFrame.add(this.chessBoardPanel, BorderLayout.CENTER);

        this.chessFrame.setJMenuBar(chessMenuBar);


        this.chessFrame.setSize(new Dimension(800,800));
        this.chessFrame.setResizable(false);
        this.chessFrame.setVisible(true);
        this.chessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

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
    }

    private class ChessTilePanel extends JPanel{
        private final int tileId;

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
            validate();
        }
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

    }
}
