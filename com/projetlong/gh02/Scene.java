package com.projetlong.gh02;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Scene {

    /** The instance of game being played */
    private final GameFrame game;
    /** The tiles used for the game. */
    private final Tiles tiles;
    /** The map used for the scene. */
    private final GameMap map;
    /** The background's tileset image. */
    private final BufferedImage backgroundTileImage;
    /** The background's tileset spritesheet. */
    private final SpriteSheet backgroundTileSheet;
    /** All of the gameobjects in the scene. */
    private final ArrayList<GameObject> gameObjects;
    /** The player. */
    private final Player player;
    /** The player's sprite sheet. */
    private final SpriteSheet playerTileSheet;
    /** The unique scene ID. */
    private final int sceneID;

    /** Creates an instance of Scene.
     * A scene has a map and an array of game objects.
     * Scenes separates different zones of the game for more
     * simple game-making.
     * @param backgroundTilePath The path to the background tile sheet
     * @param mapFile The file to load the map from
     * @param tileFile The file to load the map's tile from
     * @param game The instance of game being played
     * @param sceneID The unique scene ID
     */
    public Scene(String backgroundTilePath, File mapFile, File tileFile, GameFrame game, int sceneID) {
        this.game = game;
        this.sceneID = sceneID;

        /* Loading the assets */
        this.playerTileSheet = new SpriteSheet(game.loadImage("assets/player.png"));
        backgroundTileImage = game.loadImage(backgroundTilePath);
        backgroundTileSheet = new SpriteSheet(backgroundTileImage);

        /* Initializing the tile set. */
        this.tiles = new Tiles(tileFile, backgroundTileSheet);
        
        /* Initializing the map. */
        this.map = new GameMap(mapFile, tiles, this.game);

        /* Initializing the gameobjects. */
        gameObjects = new ArrayList<>();
        this.player = new Player(this.playerTileSheet, game.getInputHandler(), game.getRenderHandler().getCamera());
        gameObjects.add(this.player);
    }


    /** Deletes each game objects from the scene.
     * Method to call when changing scenes.
     */
    public void cleanScene() {
        gameObjects.forEach(this::deleteGameObject);
    }


    /** Returns the game map of the scene.
     * @return The game map
     */
    public GameMap getGameMap() {
        return this.map;
    }


    /** Returns the array of game objects.
     * @return The array of game objects
     */
    public ArrayList<GameObject> getGameObjects() {
        return this.gameObjects;
    }


    /** Returns the background's tile sheet.
     * @return The background's tile sheet
     */
    public SpriteSheet getBackgroundTileSheet(){
        return this.backgroundTileSheet;
    }


    /** Returns the tiles used in the scene.
     * @return The tiles used in the scene
     */
    public Tiles getTiles(){
        return this.tiles;
    }


    /** Helper method to use the for each method
     * of the gameObject array list. Can also be used
     * to delete single game objects from the scene.
     * @param object The game object to delete from the scene
     */
    public void deleteGameObject(GameObject object) {
        this.gameObjects.remove(object);
    }


    /** Returns the scene's unique ID.
     * @return The scene's unique ID
     */
    public int getSceneID() {
        return this.sceneID;
    }

}
