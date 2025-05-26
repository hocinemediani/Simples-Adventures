package com.projetlong.gh02.scene;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.entities.GameObject;
import com.projetlong.gh02.entities.NPC;
import com.projetlong.gh02.entities.Player;
import com.projetlong.gh02.map.GameMap;
import com.projetlong.gh02.map.Tiles;
import com.projetlong.gh02.menus.LoadGameMenu;
import com.projetlong.gh02.menus.MainMenu;
import com.projetlong.gh02.menus.NewGameMenu;
import com.projetlong.gh02.menus.SettingsMenu;
import com.projetlong.gh02.sprite.SpriteSheet;
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
    /** The obstacle's tileset image. */
    private final BufferedImage obstacleTileImage;
    /** The obstacle's tileset spritesheet. */
    private final SpriteSheet obstacleTileSheet;
    /** The decoration's tileset image. */
    private final BufferedImage decorationTileImage;
    /** The decoration's tileset spritesheet. */
    private final SpriteSheet decorationTileSheet;
    /** All of the gameobjects in the scene. */
    private final ArrayList<GameObject> gameObjects;
    /** The player. */
    private Player player;
    /** The NPCs of the game */
    private NPC npc1;
    private NPC npc2;
    /** The player's sprite sheet. */
    private final SpriteSheet playerTileSheet;
    /** The unique scene ID. */
    private final int sceneID;

    /** Creates an instance of Scene.
     * A scene has a map and an array of game objects.
     * Scenes separates different zones of the game for more
     * simple game-making.
     * @param backgroundTilePath The path to the background tile sheet
     * @param obstacleTilePath The path to the obstacle tile sheet
     * @param decorationTilePath The path to the decoration tile sheet
     * @param mapFile The file to load the map from
     * @param tileFile The file to load the map's tile from
     * @param game The instance of game being played
     * @param sceneID The unique scene ID
     */
    public Scene(String backgroundTilePath, String obstacleTilePath, String decorationTilePath, File mapFile, File tileFile, GameFrame game, int sceneID) {
        this.game = game;
        this.sceneID = sceneID;

        /* Loading the assets */
        this.playerTileSheet = new SpriteSheet(game.loadImage("/com/projetlong/gh02/assets/player.png"));
        backgroundTileImage = game.loadImage(backgroundTilePath);
        backgroundTileSheet = new SpriteSheet(backgroundTileImage);
        obstacleTileImage = game.loadImage(obstacleTilePath);
        obstacleTileSheet = new SpriteSheet(obstacleTileImage);
        decorationTileImage = game.loadImage(decorationTilePath);
        decorationTileSheet = new SpriteSheet(decorationTileImage);

        /* Initializing the tile set. */
        this.tiles = new Tiles(tileFile, backgroundTileSheet, obstacleTileSheet, decorationTileSheet);
        
        /* Initializing the map. */
        this.map = new GameMap(mapFile, tiles, this.game);

        /* Initializing the gameobjects. */
        gameObjects = new ArrayList<>();
        switch (sceneID) {
            case 0 -> this.gameObjects.add(new MainMenu(game));
            case 1 -> this.gameObjects.add(new SettingsMenu(game));
            case 2 -> this.gameObjects.add(new NewGameMenu(game));
            case 3 -> this.gameObjects.add(new LoadGameMenu(game));
            default -> {
                this.player = new Player(this.playerTileSheet, game.getInputHandler(), game.getRenderHandler().getCamera(), this, game, "SuperJoueur");
                this.npc1 = new NPC(this.playerTileSheet, -49, -1434);
                this.npc2 = new NPC(this.playerTileSheet, 600, 400);
                gameObjects.add(this.player);
                gameObjects.add(this.npc1);
                gameObjects.add(this.npc2);
            }
        }
        
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


    /** Returns the tile sheet following the layer.
     * @return The layer's tile sheet
     */
    public SpriteSheet getTileSheet(int layerID) {
        return switch (layerID) {
            case 1 -> obstacleTileSheet;
            case 2 -> decorationTileSheet;
            default -> backgroundTileSheet;
        };
    }


    /** Returns the tiles used in the scene.
     * @return The tiles used in the scene
     */
    public Tiles getTiles() {
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
