package com.chess.gui;

import javax.swing.*;

public class ChessTimer extends Thread {

    private final JLabel timerJLabel;
    private long startTime;
    private long currentOffset;
    private boolean running;
    private boolean continueThread;
    private long actualTimeWhenPaused;
    private long actualTimeWhenContinue;
    private final Object pauseLock = new Object();


    /**
     * Default constructor for a chess timer
     * @param timerJLabel given timer label
     */
    public ChessTimer(JLabel timerJLabel){
        this.timerJLabel = timerJLabel;
        this.continueThread = false;
        this.actualTimeWhenPaused = 0;
        this.actualTimeWhenContinue = 0;
    }

    /**
     * Method that starts a timer(thread)
     */
    public void startThread() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
        this.continueThread = true;
        this.start();
    }


    /**
     * Override thread method
     */
    @Override
    public void run(){
        while (running) {
            synchronized (pauseLock) {
                if (!running) { // may have changed while waiting to
                    // synchronize on pauseLock
                    break;
                }
                if (!continueThread) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait(); // will cause this Thread to block until
                            // another thread calls pauseLock.notifyAll()
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) { // running might have changed since we paused
                        break;
                    }
                }
            }

            //change Text on JLabel to actual time
            this.timerJLabel.setText(String.format("%02d:%02d", this.getTimeInMinutes(),
                    this.getTimeInSeconds()));
            this.timerJLabel.repaint();
            this.timerJLabel.validate();

        }
    }

    /**
     * Method that calculates time in minute with current offset
     * @return minutes
     */
    private int getTimeInMinutes() {
        long timeInMilliseconds = System.currentTimeMillis() - this.startTime - this.currentOffset;
        return (int) (timeInMilliseconds / 60000) % 60;
    }

    /**
     * Method that calculates time in seconds with current offset
     * @return seconds
     */
    private int getTimeInSeconds() {
        long timeInMilliseconds = System.currentTimeMillis() - this.startTime - this.currentOffset;
        return (int) (timeInMilliseconds / 1000) % 60 ;
    }

    /**
     * Method that stops a timer(thread)
     */
    public void stopTimer() {
        this.running = false;
    }

    /**
     * Method that pauses a timer(thread) , changing boolean continueThread to false
     */
    public void pauseTimer() {
        this.actualTimeWhenPaused = System.currentTimeMillis();
        this.continueThread = false;
    }

    /**
     * Method that continue timer(thread) and calculate offset for a timer
     */
    public void continueTimer() {
        this.actualTimeWhenContinue = System.currentTimeMillis();
        this.currentOffset += this.actualTimeWhenContinue - this.actualTimeWhenPaused;
        synchronized (pauseLock) {
            this.continueThread = true;
            pauseLock.notifyAll();
        }
    }
}
