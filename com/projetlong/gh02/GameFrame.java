package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

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
        canvas.createBufferStrategy(4);
    }

    /** The main game loop, called at each tick.
     * Most of the game logic will be inside it.
    */
    public void Update() {
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
            graphics.setColor(Color.red);
            graphics.fill3DRect(xRect + 675, yRect + 375, 100, 60, true);
            graphics.dispose();
            bufferStrategy.show();
        }
    }


    public static void main(String... args) {
        GameFrame frame = new GameFrame();
        frame.Update();
    }

}
