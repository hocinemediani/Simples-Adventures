package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements Runnable {

    /* Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();


    /** Constructor for the game frame. It initializes
     * a window with a canvas and creates a BufferStrategy
     * to render images on the window.
     */
    public GameFrame() {
        /* Option to terminate the program upon closing the window. */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Projet long");

        /* Sets base height and centers the window. */
        this.setBounds(0, 0, 1000, 800);
        this.setLocationRelativeTo(null);

        /* Maximises the window. */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(canvas);
        this.setVisible(true);

        /* Setting up the buffering strategy. */
        canvas.createBufferStrategy(3);
    }


    /** The main game loop, called at each tick.
     * Most of the game logic will be inside it.
    */
    @Override
    public void run(){
        BufferStrategy bufferStrategy;
        int theta = 0;
        int radius = 200;

        while (true) {
            theta++;
            int xRect = (int) Math.floor(radius * Math.cos(theta));
            int yRect = (int) Math.floor(radius * Math.sin(theta));
            bufferStrategy = canvas.getBufferStrategy();
            Graphics graphics = bufferStrategy.getDrawGraphics();
            super.paint(graphics);

            /* Background painting. */
            graphics.setColor(Color.white);
            graphics.drawRect(0, 0, this.getWidth(), this.getHeight());

            /* Drawing a rotating rectangle. */
            graphics.setColor(Color.red);
            graphics.fill3DRect(xRect + 675, yRect + 375, 100, 60, true);
            graphics.dispose();
            bufferStrategy.show();
        }
    }


    public static void main(String... args) {
        GameFrame game = new GameFrame();
        /* The thread is used to handle crash reports and
         * to handle update and render methods.
         */
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

}
