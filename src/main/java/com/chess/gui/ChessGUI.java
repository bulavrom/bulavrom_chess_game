package com.chess.gui;

import com.chess.engine.Board.Board;
import com.chess.engine.Board.Moves.Move;
import com.chess.engine.Board.Tile;
import com.chess.engine.Colour;
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
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import static com.chess.engine.Board.Board.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class ChessGUI {

    private boolean isFirstChange;
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
    private PrintWriter printWriter;
    JDialog gameOver;


    /**
     * Standard constructor that builds GUI
     */
    public ChessGUI() {
        this.chessFrame = new JFrame("Chess Game");
        this.settingsPanel = new SettingsPanel();
        this.chessFrame.setLayout(new BorderLayout());
        this.chessBoard = createDefaultBoard();
        try {
            this.printWriter = new PrintWriter("game.pgn","UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }


        this.chessBoardPanel = new ChessBoardPanel();
        this.legalMovesHighlighted = true;
        this.isFirstChange = true;
        this.sourceTile = null;
        this.destinationTile = null;
        this.boardDirection = BoardDirection.NORMAL;

        this.isHumanMode = settingsPanel.isHumanGame();


        final JMenuBar chessMenuBar = new JMenuBar();
//        final JMenu fileMenu = new JMenu("File");
        final JMenu boardMenu = new JMenu("Board");

        this.gameOver = new JDialog(this.chessFrame, "Game Over!", Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("Game over. The winner is " + chessBoard.getCurrentPlayer().getColour().getPlayerNameColour() + " player");
        gameOver.setLayout(new GridLayout(2, 1));
        gameOver.add(message);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameOver.add(exitButton);
        gameOver.setSize(new Dimension(400, 200));
        gameOver.setLocationRelativeTo(null);

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
        final JCheckBoxMenuItem highlightLegalMovesCheckBox = new JCheckBoxMenuItem("Highlight legal moves", true);
        highlightLegalMovesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                legalMovesHighlighted = highlightLegalMovesCheckBox.isSelected();
            }
        });

        boardMenu.add(highlightLegalMovesCheckBox);

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

        /**
         * Default constructor for a chess board panel
         */
        ChessBoardPanel() {
            super(new GridLayout(8, 8));
            this.tilesOnBoard = new ArrayList<>();
            // adding chess tile panel to the chess board panel
            for (int i = 0; i < 64; i++) {
                ChessTilePanel chessTilePanel = new ChessTilePanel(this, i);
                this.tilesOnBoard.add(chessTilePanel);
                add(chessTilePanel);
            }
            setPreferredSize(new Dimension(800, 800));
            validate();
        }

        /**
         * Method that draws an actual board
         * Also there is a simple implementation of Random Player
         * @param Board actual board
         */
        public void drawBoard(Board Board) {
            removeAll();
            for (final ChessTilePanel tilePanel : boardDirection.reverse(tilesOnBoard)) {
                tilePanel.drawTile(Board);
                add(tilePanel);
            }
            boardDirection = BoardDirection.NORMAL;

            // RANDOM PLAYER IN CASE OF NOT HUMAN GAME
            if (!settingsPanel.isHumanGame() && Board.getCurrentPlayer().getColour().isBlack()) {
                List<Move> randomPlayerLegalMoves = new ArrayList<>(Board.getCurrentPlayer().getLegalMoves());
                Move randomMove = randomPlayerLegalMoves.get(new Random().nextInt(randomPlayerLegalMoves.size()));
                final MoveJump moveJump = Board.getCurrentPlayer().makeMove(randomMove);
                if (moveJump.getMoveStatus().isDone()) {
                    chessBoard = moveJump.getBoard();
                    printWriter.write(randomMove.toString() + " ");
                    if (chessBoard.getCurrentPlayer().isInCheck()){
                        printWriter.write("+ ");
                    }else {
                        printWriter.write(" ");
                    }
                    // chess timer controls for a random player
                    if (chessBoard.getCurrentPlayer().getColour().isBlack()) {
                        settingsPanel.pauseWhitePlayerTimer();
                        settingsPanel.continueBlackPlayerTimer();
                    } else if (chessBoard.getCurrentPlayer().getColour().isWhite()) {
                        settingsPanel.pauseBlackPlayerTimer();
                        settingsPanel.continueWhitePlayerTimer();
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            drawBoard(chessBoard);
                        }
                    });
                } else if (!settingsPanel.isHumanGame() && moveJump.getMoveStatus().isCheckMove()) {
                    for (final Move move : randomPlayerLegalMoves) {
                        final MoveJump moveJumpToPreventCheck = Board.getCurrentPlayer().makeMove(move);
                        if (moveJumpToPreventCheck.getMoveStatus().isDone()) {
                            chessBoard = moveJumpToPreventCheck.getBoard();
                            printWriter.write(move.toString());
                            if (chessBoard.getCurrentPlayer().isInCheck()){
                                printWriter.write("+ ");
                            }else {
                                printWriter.write(" ");
                            }
                            // chess timer controls for a random player
                            if (chessBoard.getCurrentPlayer().getColour().isBlack()) {
                                settingsPanel.pauseWhitePlayerTimer();
                                settingsPanel.continueBlackPlayerTimer();
                            } else if (chessBoard.getCurrentPlayer().getColour().isWhite()) {
                                settingsPanel.pauseBlackPlayerTimer();
                                settingsPanel.continueWhitePlayerTimer();
                            }

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
            // end of game
            if (chessBoard.getCurrentPlayer().isInCheckMate() || chessBoard.getCurrentPlayer().isInStaleMate()) {
                if (chessBoard.getCurrentPlayer().getColour().isWhite()){
                    printWriter.write("0-1");
                    printWriter.close();
                }else {
                    printWriter.write("1-0");
                    printWriter.close();
                }
                settingsPanel.stopWhitePlayerTimer();
                settingsPanel.stopBlackPlayerTimer();
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
         * @param boardPanel board panel
         * @param tileId tile id of a tile panel
         */
        ChessTilePanel(final ChessBoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            this.setPreferredSize(new Dimension(10, 10));

            // give a background for a colour
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
                            //The second click
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveJump moveJump = chessBoard.getCurrentPlayer().makeMove(move);
                            if (moveJump.getMoveStatus().isDone()) {
                                chessBoard = moveJump.getBoard();
                                printWriter.write(move.toString());
                                if (chessBoard.getCurrentPlayer().isInCheck()){
                                    printWriter.write("+ ");
                                }else {
                                    printWriter.write(" ");
                                }
                                // chess timer controls
                                if (chessBoard.getCurrentPlayer().getColour().isBlack()) {
                                    if (isFirstChange) {
                                        settingsPanel.startBlackPlayerTimer();
                                        settingsPanel.pauseWhitePlayerTimer();
                                        isFirstChange = false;
                                    } else {
                                        settingsPanel.pauseWhitePlayerTimer();
                                        settingsPanel.continueBlackPlayerTimer();
                                    }
                                } else if (chessBoard.getCurrentPlayer().getColour().isWhite()) {
                                    settingsPanel.pauseBlackPlayerTimer();
                                    settingsPanel.continueWhitePlayerTimer();
                                }
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
            if (this.tileId == board.getCurrentPlayer().getPlayerKing().getPiecePosition()) {
                if (board.getCurrentPlayer().isInCheck() || board.getCurrentPlayer().isInCheckMate()
                        || board.getCurrentPlayer().isInStaleMate()) {
                    try {
                        removeAll();
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("icons/pieces/" +
                                board.getCurrentPlayer().getPlayerKing().getPieceColour().toString() +
                                "KC.png")))));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            validate();
            repaint();
        }

        /**
         * Method that highlight legal moves for a chosen piece (green dot for a possible default move and changes
         * the image for an attacked piece if the move is an Attack Move(piece image with red circle))
         * Also checks if player is in check, if its in check change the image for the king in check(king with the red
         * sword in the left corner)
         * @param board given board
         */
        private void highlightLegalMoves(final Board board) {
            if (legalMovesHighlighted) {
                for (final Move move : pieceLegalMoves(board)) {
                    MoveJump moveJump = board.getCurrentPlayer().makeMove(move);
                    if (moveJump.getMoveStatus().isDone()) {
                        if (move.getDestinationCoordinate() == this.tileId) {
                            try {
                                if (move.isAttackMove()) {
                                    removeAll();
                                    add(new JLabel(new ImageIcon(ImageIO.read(new File("icons/pieces/" +
                                            move.getAttackedPiece().getPieceColour().toString() +
                                            move.getAttackedPiece().getPieceType().toString() + "A.png")))));
                                } else {
                                    add(new JLabel(new ImageIcon(ImageIO.read(new File("icons/icns/greencircle.png")))));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        /**
         * Method that calculates legal moves for a moved piece
         * @param board actual board
         * @return List of legal moves for a moved piece
         */
        private Collection<Move> pieceLegalMoves(final Board board) {
            if (movedPiece != null && movedPiece.getPieceColour() == board.getCurrentPlayer().getColour()) {
                return board.getCurrentPlayer().calculateLegalPieceMoves(movedPiece.getPiecePosition());
            }
            return Collections.emptyList();
        }

    }

    /**
     * Class that extends from JPanel and represents Setting Panel on the right side of Chess Game GUI
     */
    private static class SettingsPanel extends JPanel {

        private boolean isHumanGame;
        private boolean isStarted = false;
        private final ChessTimerPanel chessTimerPanel;

        /**
         * Default constructor for Setting Panel
         */
        public SettingsPanel() {
            super(new GridLayout(2, 1));

            JPanel settingGamePanel = new JPanel(new GridLayout(4, 1));

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
                    startWhitePlayerTimer();
                }
            });

            this.chessTimerPanel = new ChessTimerPanel();



            settingGamePanel.add(settings);
            settingGamePanel.add(chooseModePanel);
            settingGamePanel.add(startButton);
            settingGamePanel.add(this.chessTimerPanel);
            this.add(settingGamePanel);
//            this.add(this.chessTimerPanel);
            setPreferredSize(new Dimension(200, 800));
            validate();
        }

        /**
         * Method to detect chosen play mode
         * @return boolean isHumanGame (if human vs human - true, else false)
         */
        public boolean isHumanGame() {
            return this.isHumanGame;
        }

        /**
         * Method to control if game started or not
         * @return boolean isStarted (if started - true , else false)
         */
        public boolean isStarted() {
            return this.isStarted;
        }

        /**
         * Method that starts White Player Timer
         */
        public void startWhitePlayerTimer() {
            this.chessTimerPanel.getWhitePlayerTimer().startTimer();
        }

        /**
         * Method that starts Black Player Timer
         */
        public void startBlackPlayerTimer() {
            this.chessTimerPanel.getBlackPlayerTimer().startTimer();
        }

        /**
         * Method that stops White Player Timer
         */
        public void stopWhitePlayerTimer() {
            this.chessTimerPanel.getWhitePlayerTimer().stopTimer();
        }

        /**
         * Method that stops Black Player Timer
         */
        public void stopBlackPlayerTimer() {
            this.chessTimerPanel.getBlackPlayerTimer().stopTimer();
        }

        /**
         * Method that pauses White Player Timer
         */
        public void pauseWhitePlayerTimer() {
            this.chessTimerPanel.getWhitePlayerTimer().pauseTimer();
        }

        /**
         * Method that continue White Player Timer
         */
        public void continueWhitePlayerTimer() {
            this.chessTimerPanel.getWhitePlayerTimer().continueTimer();
        }

        /**
         * Method that pause Black Player Timer
         */
        public void pauseBlackPlayerTimer() {
            this.chessTimerPanel.getBlackPlayerTimer().pauseTimer();
        }

        /**
         * Method that continue Black Player Timer
         */
        public void continueBlackPlayerTimer() {
            this.chessTimerPanel.getBlackPlayerTimer().continueTimer();
        }
    }

    /**
     * Class that extends from JPanel and represents Timer section on the Setting Panel
     */
    private static class ChessTimerPanel extends JPanel {
        private final TimerLabel whitePlayerTimer;
        private final TimerLabel blackPlayerTimer;


        /**
         * Default constructor for Chess Timer Panel
         */
        public ChessTimerPanel() {
            super(new GridLayout(3, 1));

            JPanel timersSection = new JPanel(new GridLayout(1, 2));

            this.whitePlayerTimer = new TimerLabel(Colour.WHITE);
            this.blackPlayerTimer = new TimerLabel(Colour.BLACK);


            JLabel timerLabel = new JLabel("Timer");
            timerLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
            timerLabel.setHorizontalAlignment(JLabel.CENTER);
            timerLabel.setVerticalAlignment(JLabel.CENTER);

            add(timerLabel);

            JPanel timerNamesSection = new JPanel(new GridLayout(1, 2));
            JLabel whitePlayerLabel = new JLabel("White:");
            JLabel blackPlayerLabel = new JLabel("Black:");
            whitePlayerLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
            whitePlayerLabel.setHorizontalAlignment(JLabel.CENTER);
            blackPlayerLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
            whitePlayerLabel.setHorizontalAlignment(JLabel.CENTER);

            timerNamesSection.add(whitePlayerLabel);
            timerNamesSection.add(blackPlayerLabel);

            timersSection.add(this.whitePlayerTimer);
            timersSection.add(this.blackPlayerTimer);


            add(timerNamesSection);
            add(timersSection);
            validate();
            this.setVisible(true);
        }

        /**
         * Getter for White Timer Label (timer)
         * @return white timer label
         */
        public TimerLabel getWhitePlayerTimer() {
            return this.whitePlayerTimer;
        }

        /**
         * Getter for Black Timer Label (timer)
         * @return black timer label
         */
        public TimerLabel getBlackPlayerTimer() {
            return this.blackPlayerTimer;
        }
    }

    /**
     * Class that represents Timer Label and timer logic
     */
    private static class TimerLabel extends JLabel {
        int actualTimeInMinutes;
        int actualTimeInSeconds;
        Colour colourOfPlayer;
        ChessTimer chessTimer;


        /**
         * Default constructor for Timer Label
         * @param colourOfPlayer colour of player
         */
        public TimerLabel(final Colour colourOfPlayer) {
            this.colourOfPlayer = colourOfPlayer;
            this.chessTimer = new ChessTimer(this);
            this.setFont(new Font("Verdana", Font.PLAIN, 20));
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setVerticalAlignment(JLabel.CENTER);
            this.actualTimeInMinutes = 0;
            this.actualTimeInSeconds = 0;
            this.setText(String.format("%02d:%02d", this.actualTimeInMinutes, this.actualTimeInSeconds));
        }


        /**
         * Method that starts timer
         */
        public void startTimer() {
            this.chessTimer.startThread();
        }


        /**
         * Method that stop timer
         */
        public void stopTimer() {
            this.chessTimer.stopTimer();
        }

        /**
         * Method that pause timer
         */
        public void pauseTimer() {
            this.chessTimer.pauseTimer();
        }

        /**
         * Method that
         */
        public void continueTimer() {
            this.chessTimer.continueTimer();
        }
    }
}
