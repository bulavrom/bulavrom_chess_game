package com.chess.gui;

public class ChessTimer extends Thread{

    private long startTime;
    private boolean running;

    public void startThread(){
        this.startTime = System.currentTimeMillis();
        this.running = true;
        this.start();

    }

    @Override
    public void run(){
        while (running){

        }
    }

    public int getTimeInMinutes(){
        long timeInMilliseconds = System.currentTimeMillis() - this.startTime;
        return (int)(timeInMilliseconds / 60000) % 60;
    }

    public int getTimeInSeconds(){
        long timeInMilliseconds = System.currentTimeMillis() - this.startTime;
        return (int) (timeInMilliseconds / 1000) % 60;
    }


    public void stopTimer(){
        this.running = false;
    }
}
