package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGUI {

    private final JFrame chessFrame;

    public ChessGUI(){
        this.chessFrame = new JFrame("Chess Game");

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

        this.chessFrame.setJMenuBar(chessMenuBar);

        this.chessFrame.setSize(new Dimension(800,800));
        this.chessFrame.setVisible(true);
        this.chessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
