package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements Runnable {
    
    /** Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();
    /** The render handler of the game. */
    private final RenderHandler renderHandler;
    /** The input handler of the game. */
    private final InputHandler inputHandler;
    /** The global scale used to render our tiles. */
    public static final int GLOBALSCALE = 3;
    /** Random color that we will not use, to create transparency */
    public static int ALPHA = 0x8b0be0;
    /** The tiles used for the game. */
    private final Tiles tiles;
    /** The map used for the test level. */
    private final GameMap map;
    /** For testing purposes. */
    private final Rectangle testRectangle = new Rectangle(50, 50, 350, 250);
    /** For testing purposes. */
    public final BufferedImage backgroundTileImage;
    /** For testing purposes. */
    private final SpriteSheet backgroundTileSheet;

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
        this.setResizable(true);

        /* Setting up the buffering strategy. */
        canvas.createBufferStrategy(3);

        /* Assignations of the handlers. */
        renderHandler = new RenderHandler(this.getWidth(), this.getHeight());
        inputHandler = new InputHandler();
        
        /* Loading of the assets */
        backgroundTileImage = loadImage("assets/backgroundTileSheet.png");
        backgroundTileSheet = new SpriteSheet(backgroundTileImage);

        /* Initializing the Tile set. */
        File tilesFile = new File("com/projetlong/gh02/tiles.txt");
        this.tiles = new Tiles(tilesFile, backgroundTileSheet);
        /* Initializing the map. */
        File mapFile = new File("com/projetlong/gh02/firstLevel.txt");
        this.map = new GameMap(mapFile, this.tiles);
    }


    /** Loads and convert images to the correct format
     * to prepare them for rendering.
     * @param path The path to the image
     * @return The loaded image in the correct format
     */
    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(GameFrame.class.getResource(path));
            BufferedImage convertedImage = new BufferedImage(loadedImage.getWidth(),
                                            loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return convertedImage;
        } catch (IOException e) {
            System.out.println("No image available at location" + path);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect input : " + path);
            return null;
        } catch (NullPointerException e) {
            System.out.println("Path does not exist or is mispelled.");
            return null;
        }
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

        /* Loading the different  tiles to render. */
        testRectangle.generateBorderGraphics(5, 0x000000);
        this.map.loadMap(renderHandler, GLOBALSCALE);
        try {
            renderHandler.loadSprite(backgroundTileSheet.getSprite(1, 1),
                                    250, 55, GLOBALSCALE * 5);
        } catch (NullPointerException e) {
            System.out.println("No sprite at specified location.");
        }
        tiles.load(1, renderHandler, 900, 500, GLOBALSCALE);
        renderHandler.loadRectangle(testRectangle, GLOBALSCALE);

        /* Clearing the graphics and rendering what has been painted. */
        renderHandler.render(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }


    /** The main game loop, called at each tick. */
    @Override
    public void run(){
        boolean isRunning = true;

        int desiredFPS = 60;
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
