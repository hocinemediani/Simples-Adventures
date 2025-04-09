package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements Runnable {

    /* Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();

    private final RenderHandler renderHandler;


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
        this.setResizable(false);

        /* Setting up the buffering strategy. */
        canvas.createBufferStrategy(3);

        renderHandler = new RenderHandler(this.getWidth(), this.getHeight());
    }


    /** Method to update the game at a certain speed.
     * This method will update the game at a certain
     * rate no matter what the system abilities are.
     * Units of time here are refered to as "ticks".
     */
    public void update() {

    }

    /** Method to render the game to the screen. Its
     * speed depends on hardware. This is where frames
     * are handled.
     */
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);
        renderHandler.render(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }


    /** The main game loop, called at each tick.
     * Most of the game logic will be inside it.
    */
    @Override
    public void run(){
        boolean isRunning = true;

        int desiredFPS = 144;
        double toSeconds = 1000000000 / desiredFPS;
        Long previousTime = System.nanoTime();
        double deltaTime = 0;

        while (isRunning) {
            Long currentTime = System.nanoTime();
            deltaTime += (currentTime - previousTime) / toSeconds;

            if (deltaTime >= 1) {
                this.update();
                deltaTime = 0;
            }

            this.render();
            previousTime = currentTime;
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
