package com.projetlong.gh02;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements Runnable {
    
    /** Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();
    /** The render handler of the game. */
    private final RenderHandler renderHandler;
    /** The input handler of the game. */
    private final InputHandler inputHandler;
    /**  */
    private final MouseInputHandler mouseInputHandler;
    /** The global scale used to render our tiles. */
    public static final int GLOBALSCALE = 2;
    /** Random color that we will not use, to create transparency */
    public static int ALPHA = 0x8b0be0;
    /** The tiles used for the game. */
    private final Tiles tiles;
    /** The map used for the test level. */
    private final GameMap map;
    /** The background's tileset image. */
    private final BufferedImage backgroundTileImage;
    /** The background's tileset spritesheet. */
    private final SpriteSheet backgroundTileSheet;
    /** All of the gameobjects in the game. */
    private final ArrayList<GameObject> gameObjects;
    /**  */
    private final Player player;
    /**  */
    private final SpriteSheet playerTileSheet;

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
        mouseInputHandler = new MouseInputHandler(this);
        
        /* Loading of the assets */
        backgroundTileImage = loadImage("assets/backgroundTileSheet.png");
        backgroundTileSheet = new SpriteSheet(backgroundTileImage);
        playerTileSheet = new SpriteSheet(loadImage("assets/player.png"));

        /* Initializing the tile set. */
        File tilesFile = new File("com/projetlong/gh02/tiles.txt");
        this.tiles = new Tiles(tilesFile, backgroundTileSheet);

        /* Initializing the map. */
        File mapFile = new File("com/projetlong/gh02/testLevel2.txt");
        this.map = new GameMap(mapFile, this.tiles);

        /* Initializing the gameobjects. */
        gameObjects = new ArrayList<>();
        player = new Player(playerTileSheet, inputHandler, renderHandler.getCamera());
        gameObjects.add(player);

        /* Adding the key listener. */
        canvas.addKeyListener(inputHandler);
        canvas.addFocusListener(inputHandler);
        canvas.addMouseListener(mouseInputHandler);
        canvas.addMouseMotionListener(mouseInputHandler);
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
        for (GameObject gameObject : gameObjects) {
            gameObject.update(this);
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

        /* Loading the different tiles from the map to render. */
        this.map.loadMap(renderHandler, GLOBALSCALE);

        /* Rendering all of the game objects.
         * WILL NEED TO IMPLEMENT RENDERING BY LAYERS.
         */
        for (GameObject gameObject : gameObjects) {
            gameObject.render(renderHandler, GLOBALSCALE);
        }

        /* Clearing the graphics and rendering what has been painted. */
        renderHandler.render(graphics);
        graphics.dispose();
        bufferStrategy.show();
        renderHandler.clear();
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

    /**  */
    public InputHandler getInputHandler(){
        return this.inputHandler;
    }


    /**  */
    public RenderHandler getRenderHandler(){
        return this.renderHandler;
    }


    /**  */
    public MouseInputHandler getMouseInputHandler(){
        return this.mouseInputHandler;
    }


    /**  */
    public SpriteSheet getBackgroundTileSheet(){
        return this.backgroundTileSheet;
    }


    /**  */
    public Tiles getTiles(){
        return this.tiles;
    }


    /**  */
    public GameMap getGameMap(){
        return this.map;
    }


    public static void main(String... args) {
        GameFrame game = new GameFrame();
        /* Thread is used for crash reports / better control. */
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
