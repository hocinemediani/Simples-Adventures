package com.projetlong.gh02.map;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.sprite.SpriteSheet;

public class MapEditor {

    /** The instance of game being played. */
    private final GameFrame game;
    /** The camera linked to the player. */
    private final Rectangle camera;
    /** The ID of the selected tile. */
    private int tileID;
    /** The ID of the selected layer. */
    private int layerID;
    private static final int MAX_LAYERS = 3; // Modifie selon le nombre de layers souhaité
    /** The hashmap used to identify the sprites of the different layers */
    private final HashMap<Integer, ArrayList<Integer>> layerToSprite = new HashMap<>();

    /** Creates an instance of MapEditor.
     * A Map Editor helps in the process of map
     * construction by providing UI and simple commands
     * to place, delete and shuffle through tiles.
     * @param game The instance of game being played
     */
    public MapEditor(GameFrame game) {
        this.game = game;
        this.camera = this.game.getRenderHandler().getCamera();
        layerToSprite.put(0, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7))); // background
        layerToSprite.put(1, new ArrayList<>(Arrays.asList(8, 9, 10, 11)));       // obstacle
        layerToSprite.put(2, new ArrayList<>(Arrays.asList(12, 13, 14, 15, 16, 17)));    // decoration
    }


    public void nextLayer() {
        layerID = (layerID + 1) % MAX_LAYERS;
    }
    public void previousLayer() {
        layerID = (layerID - 1 + MAX_LAYERS) % MAX_LAYERS;
    }
    public int getLayerID() {
        return layerID;
    }

    /** Handles input from a mouse event.
     * Depending on the mouse button clicked, this
     * method maps it to an action and executes
     * the action.
     * @param e The mouse event to handle
     */
    public void handleInput(MouseEvent e) {
        int numTiles = layerToSprite.get(getLayerID()).size();
        int tileLength = SpriteSheet.tileSize * GameFrame.GLOBALSCALE;
        int xPos = (((int) e.getX() + this.camera.getX()) / (tileLength));
        int yPos = (((int) e.getY() + this.camera.getY()) / (tileLength));
        if (xPos * tileLength <= 0) {
            xPos = (((int) e.getX() + this.camera.getX() - tileLength) / (tileLength));
        }
        if (yPos * tileLength <= 0) {
            yPos = (((int) e.getY() + this.camera.getY() - tileLength) / (tileLength));
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            placeTile(xPos, yPos);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            this.game.getSceneManager().getCurrentScene().getGameMap().deleteMappedTile(xPos, yPos, layerID);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            tileID = (tileID + 1) % numTiles;
        }
    }


    /** Places a tile of ID tileID at position
     * (xPos, yPos) when seeing the game map as
     * a grid of tiles.
     * @param xPos The x-position of the tile
     * @param yPos The y-position of the tile
     */
    public void placeTile(int xPos, int yPos) {
        int[] tileInfo = new int[4];
        tileInfo[0] = layerToSprite.get(layerID).get(tileID);
        tileInfo[1] = xPos;
        tileInfo[2] = yPos;
        tileInfo[3] = layerID;
        //if (layerID > this.game.getSceneManager().getCurrentScene().getGameMap().getMaxLayerAtPosition(xPos, yPos)) {
            this.game.getSceneManager().getCurrentScene().getGameMap().addMappedTile(tileInfo);
            String mapString = xPos  + "," + yPos;
            this.game.getSceneManager().getCurrentScene().getGameMap().writeToFile(mapString, tileInfo[0], layerID);
        //}
    }



    /** Renders the construction mode's UI.
     * This UI consists of the current layer the user
     * is building on, a preview of the different tiles
     * available on this layer and a rectangle highlighting
     * the currently selected tile.
     */
    public void renderUI() {
        ArrayList<Integer> tileIDs = layerToSprite.get(getLayerID());
        int numTiles = tileIDs.size();
        int tileUIScale = 4;
        int tileLength = SpriteSheet.tileSize * tileUIScale;
        for (int i = 0; i < numTiles; i++) {
            int tileID = tileIDs.get(i);
            game.getRenderHandler().loadSprite(game.getSceneManager().getCurrentScene().getTileSheet(layerID).getSprite(i % numTiles, i / numTiles),
                                                camera.getX() + i * tileLength,
                                                camera.getY(), tileUIScale);
            if (i == this.tileID) {
                Rectangle currentTile = new Rectangle(camera.getX() + i * tileLength,
                                                    camera.getY(), SpriteSheet.tileSize, SpriteSheet.tileSize);
                currentTile.generateBorderGraphics(1, 0xFFFFFF);
                game.getRenderHandler().loadRectangle(currentTile, tileUIScale);
            }
        }
    }
    
}
