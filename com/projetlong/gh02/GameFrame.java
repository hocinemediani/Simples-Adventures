package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements Runnable {

    /* Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();
    private final RenderHandler renderHandler;
    private final BufferedImage testBackgroundImage;

    private final Player player;
    private final boolean[] keys = new boolean[256];

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
        testBackgroundImage = loadImage("assets/grassTile.png");

        player = new Player();

        // Setup keyboard input
        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });
    }

    /** Method to update the game at a certain speed. */
    public void update() {
        player.update(keys);
    }

    /** Loads and convert images to the correct format. */
    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(GameFrame.class.getResource(path));
            BufferedImage convertedImage = new BufferedImage(
                    loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return convertedImage;
        } catch (IOException e) {
            System.out.println("No image available at location " + path);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect input: " + path);
            return null;
        }
    }

    /** Method to render the game to the screen. */
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        // Draw background
        for (int x = 0; x < this.getWidth() - testBackgroundImage.getWidth(); x += testBackgroundImage.getWidth()) {
            for (int y = 0; y < this.getHeight() - testBackgroundImage.getHeight(); y += testBackgroundImage.getHeight()) {
                renderHandler.loadImageData(testBackgroundImage, x, y);
            }
        }

        renderHandler.render(graphics);

        // Draw player
        player.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    /** The main game loop */
    @Override
    public void run() {
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
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
