package com.projetlong.gh02;

import com.projetlong.gh02.entities.GameObject;
import com.projetlong.gh02.handlers.InputHandler;
import com.projetlong.gh02.handlers.MouseInputHandler;
import com.projetlong.gh02.handlers.RenderHandler;
import com.projetlong.gh02.scene.Scene;
import com.projetlong.gh02.scene.SceneLoader;
import com.projetlong.gh02.scene.SceneManager;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GameFrame extends JFrame implements Runnable {
    
    /** Canvas onto which the frame will draw. */
    private final Canvas canvas = new Canvas();
    /** The render handler of the game. */
    private final RenderHandler renderHandler;
    /** The input handler of the game. */
    private final InputHandler inputHandler;
    /** The mouse input handler of the game. */
    private final MouseInputHandler mouseInputHandler;
    /** The global scale used to render our tiles. */
    public static final int GLOBALSCALE = 3;
    /** Random color that we will not use, to create transparency */
    public static int ALPHA = 0x8b0be0;
    /** The scene manager of the game. */
    private final SceneManager sceneManager;
    /** The scene loader of the game. */
    private final SceneLoader sceneLoader;
    private final ImageIcon logo = new ImageIcon("com/projetlong/gh02/assets/logo32.png");
    /** The current scene being displayed. */
    private Scene currentScene;

    /** Constructor for the game frame. It initializes
     * a window with a canvas and creates a BufferStrategy
     * to render images on the window.
     */
    public GameFrame() {
        this.setTitle("Projet long");

        /* Sets base height and centers the window. */
        this.setBounds(0, 0, 1000, 800);
        this.setLocationRelativeTo(null);

        /* Maximises the window. */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(canvas);
        this.setIconImage(logo.getImage());
        this.setVisible(true);
        this.setResizable(true);

        /* Setting up the buffering strategy. */
        canvas.createBufferStrategy(3);

        /* Assignations of the handlers. */
        renderHandler = new RenderHandler(this.getWidth(), this.getHeight());
        inputHandler = new InputHandler(this);
        mouseInputHandler = new MouseInputHandler(this);

        /** Loading in the first scene. */
        this.sceneManager = new SceneManager(this);
        this.currentScene = sceneManager.getCurrentScene();
        this.sceneLoader = new SceneLoader(this);

        /* Adding the listeners to the canvas. */
        canvas.addKeyListener(inputHandler);
        canvas.addFocusListener(inputHandler);
        canvas.addMouseListener(mouseInputHandler);
        canvas.addMouseMotionListener(mouseInputHandler);

        /* Option to terminate the program upon closing the window.
         * The map text file is also being cleaned up.
         */
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Scene scene : getSceneManager().getScenes()) {
                    scene.getGameMap().cleanupMapFile();
                }
                dispose();
                System.exit(0);
            }
        });
    }


    /** Loads and convert images to the correct format
     * to prepare them for rendering.
     * @param path The path to the image
     * @return The loaded image in the correct format
     */
    public BufferedImage loadImage(String path) {
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
        for (GameObject gameObject : currentScene.getGameObjects()) {
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
        this.currentScene.getGameMap().loadMap(GLOBALSCALE);

        if (inputHandler.isInContructionMode()) {
            this.currentScene.getGameMap().getMapEditor().renderUI();
        }
    
        /* Rendering all of the game objects. */
        for (GameObject gameObject : currentScene.getGameObjects()) {
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


    /** Updates the current scene to display
     * it's map and game objects.
     */
    public void updateCurrentScene() {
        this.currentScene = sceneManager.getCurrentScene();
    }


    /** Returns the input handler.
     * @return The input handler
     */
    public InputHandler getInputHandler(){
        return this.inputHandler;
    }


    /** Returns the render handler.
     * @return The render handler
     */
    public RenderHandler getRenderHandler(){
        return this.renderHandler;
    }


    /** Returns the mouse input handler.
     * @return The mouse input handler
     */
    public MouseInputHandler getMouseInputHandler(){
        return this.mouseInputHandler;
    }


    /** Returns the scene manager.
     * @return The scene manager
     */
    public SceneManager getSceneManager() {
        return this.sceneManager;
    }


    /** Returns the scene loader.
     * @return The scene loader
     */
    public SceneLoader getSceneLoader() {
        return this.sceneLoader;
    }


    public static void main(String... args) {
        GameFrame game = new GameFrame();
        /* Thread is used for crash reports / better control. */
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
