package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


import java.awt.event.*;
import java.awt.*;

public class GameFrame extends JFrame implements Runnable {
    
    /* Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();
    private final RenderHandler renderHandler;
    private InputHandler inputHandler;
    private final BufferedImage testBackgroundImage;
    private final BufferedImage menuBackround;

    /* Menu variables. */
    private boolean isInMenu = true;
    private boolean isNameEntered = false;
    private String playerName = "";

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
        inputHandler = new InputHandler();
        testBackgroundImage = loadImage("assets/grassTile.png");
        menuBackround = loadImage("assets/Menu_background.png");

        /* analysing the inputs */
        setupInput();
    }

    private void setupInput() {
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                /* typing the input*/
                if (isInMenu && !isNameEntered) {
                    char c = e.getKeyChar();
                    if (c == '\b' && playerName.length() > 0){
                        /* Delete the last character of the string */
                        char[] chars = playerName.toCharArray();      
                        String newName = "";                          

                        for (int i = 0; i < chars.length - 1; i++) {
                            newName += chars[i];
                        }

                        playerName = newName;
                    } else if (c == '\b' && playerName.length() == 0) {
                        /* nothing to do */
                    } else {
                        playerName += c;
                    }
                }
            }

            @Override
        public void keyPressed(KeyEvent e) {
            /* pressed keys processing */
            if (isInMenu && !isNameEntered && e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!playerName.trim().isEmpty()) {
                    isInMenu = false;
                    isNameEntered = true;
                    System.out.println("Starting game for: " + playerName);
                }
            }
        }
        });

        
        // focus on the canvas for key events
        canvas.setFocusable(true);
        canvas.requestFocus();
    }


    /** Method to update the game at a certain speed.
     * This method will update the game at a certain
     * rate no matter what the system abilities are.
     * Units of time here are refered to as "ticks".
     */
    public void update() {

    }


    /** Loads and convert images to the correct format
     * to prepare them for rendering.
     * @param path The path to the image
     * @return The loaded image in the correct format
     */
    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(GameFrame.class.getResource(path));
            BufferedImage convertedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return convertedImage;
        } catch (IOException e) {
            System.out.println("No image available at location" + path);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect input : " + path);
            return null;
        }
    }


    /** Method to render the game to the screen. Its
     * speed depends on hardware. This is where frames
     * are handled.
     */
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        
        if (isInMenu) {
            /* Loading the Menu background */

            
            //* Loading the background in. */
            graphics.drawImage(menuBackround, 0, 0, getWidth(), getHeight(), this);

            // Text format and color
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 32));
            graphics.drawString(playerName + "_", 400, 378);
          
        } else {
            /* Loading the background in. */
        for (int x = 0; x < this.getWidth(); x += testBackgroundImage.getWidth()) {
            for (int y = 0; y < this.getHeight(); y += testBackgroundImage.getHeight()) {
                renderHandler.loadImageData(testBackgroundImage, x, y, 1);
            }
        }

        renderHandler.render(graphics);
        }

        

        /* Clearing the graphics and rendering what has been painted. */
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
        /* Thread is used for crash reports / better control. */
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
