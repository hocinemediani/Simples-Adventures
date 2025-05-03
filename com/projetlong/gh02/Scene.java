package com.projetlong.gh02;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Scene {

    /**  */
    private final GameFrame game;
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
    /**  */
    private final int sceneID;

    /**  */
    public Scene(String backgroundTilePath, File mapFile, File tileFile, GameFrame game, int sceneID) {
        this.game = game;
        this.sceneID = sceneID;

        /* Loading of the assets */
        this.playerTileSheet = new SpriteSheet(game.loadImage("assets/player.png"));
        backgroundTileImage = game.loadImage(backgroundTilePath);
        backgroundTileSheet = new SpriteSheet(backgroundTileImage);

        /* Initializing the tile set. */
        this.tiles = new Tiles(tileFile, backgroundTileSheet);
        
        /* Initializing the map. */
        this.map = new GameMap(mapFile, tiles, game);

        /* Initializing the gameobjects. */
        gameObjects = new ArrayList<>();
        this.player = new Player(this.playerTileSheet, game.getInputHandler(), game.getRenderHandler().getCamera());
        gameObjects.add(this.player);
    }


    /**  */
    public void cleanScene() {
        gameObjects.forEach(this::deleteGameObject);
    }


    /**  */
    public GameMap getGameMap() {
        return this.map;
    }


    /**  */
    public ArrayList<GameObject> getGameObjects() {
        return this.gameObjects;
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
    public void deleteGameObject(GameObject object) {
        this.gameObjects.remove(object);
    }


    /**  */
    public int getSceneID() {
        return this.sceneID;
    }

}
