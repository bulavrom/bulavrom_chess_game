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
import java.util.*;
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
    private SettingsPanel settingsPanel;
    private boolean legalMovesHighlighted;
    private BoardDirection boardDirection;
    private final String pieceImagePath = "icons/pieces/";
    private boolean isHumanMode;
    JDialog gameOver;


    /**
     * Standard constructor that builds GUI
     */
    public ChessGUI() {
        this.chessFrame = new JFrame("Chess Game");
        this.settingsPanel = new SettingsPanel();
        this.chessFrame.setLayout(new BorderLayout());
        this.chessBoard = createDefaultBoard();


        this.chessBoardPanel = new ChessBoardPanel();
        this.legalMovesHighlighted = false;
        this.sourceTile = null;
        this.destinationTile = null;
        this.boardDirection = BoardDirection.NORMAL;

        this.isHumanMode = settingsPanel.isHumanGame();


        final JMenuBar chessMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu boardMenu = new JMenu("Board");
        final JMenu settingsMenu = new JMenu();

        this.gameOver = new JDialog(this.chessFrame, "Game Over!",Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("Game over. The winner is " + chessBoard.getCurrentPlayer().getColour().getPlayerNameColour() + " player");
        gameOver.setLayout(new GridLayout(2,1));
        gameOver.add(message);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameOver.add(exitButton);
        gameOver.setSize(new Dimension(400,200));
        gameOver.setLocationRelativeTo(null);

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
        final JCheckBoxMenuItem highlightLegalMovesCheckBox = new JCheckBoxMenuItem("Highlight legal moves", false);
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
        this.chessFrame.add(this.settingsPanel, BorderLayout.EAST);

        this.chessFrame.setJMenuBar(chessMenuBar);

        this.chessFrame.setSize(new Dimension(1000, 800));
        this.chessFrame.setResizable(false);
        this.chessFrame.setVisible(true);
        this.chessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    /**
     * Class that represents Board in GUI as a class that extends from JPanel
     * contains 64 ChessTilePanels
     */
    private class ChessBoardPanel extends JPanel {
        final List<ChessTilePanel> tilesOnBoard;

        ChessBoardPanel() {
            super(new GridLayout(8, 8));
            this.tilesOnBoard = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                ChessTilePanel chessTilePanel = new ChessTilePanel(this, i);
                this.tilesOnBoard.add(chessTilePanel);
                add(chessTilePanel);
            }
            //   setPreferredSize(new Dimension(500, 500));
            setPreferredSize(new Dimension(800, 800));
            validate();
        }

        public void drawBoard(Board Board) {
            removeAll();
            for (final ChessTilePanel tilePanel : boardDirection.reverse(tilesOnBoard)) {
                tilePanel.drawTile(Board);
                add(tilePanel);
            }
            boardDirection = BoardDirection.NORMAL;
            if (!settingsPanel.isHumanGame() && Board.getCurrentPlayer().getColour().isBlack()) {
                List<Move> randomPlayerLegalMoves = new ArrayList<>(Board.getCurrentPlayer().getLegalMoves());
                Move randomMove = randomPlayerLegalMoves.get(new Random().nextInt(randomPlayerLegalMoves.size()));
                final MoveJump moveJump = Board.getCurrentPlayer().makeMove(randomMove);
                if (moveJump.getMoveStatus().isDone()) {
                    chessBoard = moveJump.getBoard();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            drawBoard(chessBoard);
                        }
                    });
                } else if (moveJump.getMoveStatus().isCheckMove()) {
                    for (final Move move : randomPlayerLegalMoves) {
                        final MoveJump moveJumpToPreventCheck = Board.getCurrentPlayer().makeMove(move);
                        if (moveJumpToPreventCheck.getMoveStatus().isDone()) {
                            chessBoard = moveJumpToPreventCheck.getBoard();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    drawBoard(chessBoard);
                                }
                            });
                        }

                    }
                }
                validate();
            }
            validate();
            repaint();
            if (chessBoard.getCurrentPlayer().isInCheckMate() || chessBoard.getCurrentPlayer().isInStaleMate()){
                gameOver.setVisible(true);
            }
        }


    }


    /**
     * Class that represents Tile in GUI as a class that extends from JPanel
     */
    public class ChessTilePanel extends JPanel {
        private final int tileId;

        /**
         * Constructor for a Tile panel
         *
         * @param boardPanel
         * @param tileId
         */
        ChessTilePanel(final ChessBoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            this.setPreferredSize(new Dimension(10, 10));

            if (((this.tileId + (this.tileId / 8)) % 2) == 0) {
                setBackground(Color.decode("#FFF08F"));
            } else {
                setBackground(Color.decode("#B57912"));
            }
            addPieceImage(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Right click to cancel
                    if (isRightMouseButton(e)) {
                        //delete after debug
                        System.out.println("Human mode is " + settingsPanel.isHumanGame());
                        //System.out.println(chessBoard.getTile(tileId).getPiece().getPieceColour().toString() + chessBoard.getTile(tileId).getPiece().getPieceType().toString());
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;


                    } else if (isLeftMouseButton(e)) {
                        //The first click
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            movedPiece = sourceTile.getPiece();
                            if (movedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveJump moveJump = chessBoard.getCurrentPlayer().makeMove(move);
                            if (moveJump.getMoveStatus().isDone()) {
                                chessBoard = moveJump.getBoard();
                            }
                            sourceTile = null;
                            destinationTile = null;
                            movedPiece = null;
                        }
                        if (settingsPanel.isStarted()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    boardPanel.drawBoard(chessBoard);
                                }
                            });
                        }
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
         *
         * @param board given board
         */
        private void addPieceImage(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage imagePiece = ImageIO.read(new File(pieceImagePath + board.getTile(this.tileId).getPiece().getPieceColour().toString() +
                            board.getTile(this.tileId).getPiece().getPieceType().toString() + ".png"));
                    add(new JLabel(new ImageIcon(imagePiece)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * Method that draws a tile
         *
         * @param board given board
         */
        public void drawTile(final Board board) {
            if (((this.tileId + (this.tileId / 8)) % 2) == 0) {
                setBackground(Color.decode("#FFF08F"));
            } else {
                setBackground(Color.decode("#B57912"));
            }
            addPieceImage(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }

        private void highlightLegalMoves(final Board board) {
            if (legalMovesHighlighted) {
                for (final Move move : pieceLegalMoves(board)) {
                    MoveJump moveJump = board.getCurrentPlayer().makeMove(move);
                    if (moveJump.getMoveStatus().isDone()) {
                        if (move.getDestinationCoordinate() == this.tileId) {
                            try {
                                add(new JLabel(new ImageIcon(ImageIO.read(new File("icons/icns/greencircle.png")))));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (movedPiece != null && movedPiece.getPieceColour() == board.getCurrentPlayer().getColour()) {
//                return movedPiece.calculateLegalMoves(board);
                return board.getCurrentPlayer().calculateLegalPieceMoves(movedPiece.getPiecePosition());
            }
            return Collections.emptyList();
        }

    }

    private static class SettingsPanel extends JPanel {

        boolean isHumanGame;
        boolean isStarted = false;

        public SettingsPanel() {
            super(new GridLayout(5, 1));

            JLabel settings = new JLabel("Settings");
            settings.setFont(new Font("Verdana", Font.PLAIN, 30));
            settings.setHorizontalAlignment(JLabel.CENTER);
            settings.setVerticalAlignment(JLabel.CENTER);
            this.isHumanGame = true;

            JPanel chooseModePanel = new JPanel(new GridLayout(2, 1));
            JRadioButton computerModePlay = new JRadioButton("Computer vs Human Mode", false);
            computerModePlay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isHumanGame = false;
                }
            });
            JRadioButton humanModePlay = new JRadioButton("Human vs Human", true);
            humanModePlay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isHumanGame = true;
                }
            });

            ButtonGroup chooseModeButtons = new ButtonGroup();
            chooseModeButtons.add(computerModePlay);
            chooseModeButtons.add(humanModePlay);

            chooseModePanel.add(computerModePlay);
            chooseModePanel.add(humanModePlay);

            chooseModePanel.setSize(new Dimension(200, 100));

            JButton startButton = new JButton("START");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isStarted = true;
                    humanModePlay.setEnabled(false);
                    computerModePlay.setEnabled(false);
                    startButton.setEnabled(false);
                }
            });
            add(settings);
            add(chooseModePanel);
            add(startButton);
            setPreferredSize(new Dimension(200, 800));
            validate();
        }

        public boolean isHumanGame() {
            return this.isHumanGame;
        }

        public boolean isStarted() {
            return this.isStarted;
        }
    }
}
