package com.projetlong.gh02;

import java.awt.event.MouseEvent;

public class MapEditor {

    /** The instance of game being played. */
    private final GameFrame game;
    /** The camera linked to the player. */
    private final Rectangle camera;
    /** The ID of the selected tile. */
    private int tileID;

    /** Creates an instance of MapEditor.
     * A Map Editor helps in the process of map
     * construction by providing UI and simple commands
     * to place, delete and shuffle through tiles.
     * @param game The instance of game being played
     */
    public MapEditor(GameFrame game) {
        this.game = game;
        this.camera = this.game.getRenderHandler().getCamera();
    }


    /** Handles input from a mouse event.
     * Depending on the mouse button clicked, this
     * method maps it to an action and executes
     * the action.
     * @param e The mouse event to handle
     */
    public void handleInput(MouseEvent e) {
        int numTiles = game.getCurrentScene().getTiles().getNumberOfTiles();
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
            this.game.getCurrentScene().getGameMap().deleteMappedTile(xPos, yPos);
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
        int[] tileInfo = new int[3];
        tileInfo[0] = tileID;
        tileInfo[1] = xPos;
        tileInfo[2] = yPos;
        this.game.getCurrentScene().getGameMap().addMappedTile(tileInfo);
        String mapString = xPos  + "," + yPos;
        this.game.getCurrentScene().getGameMap().writeToFile(mapString, tileID);
    }



    /** Renders the construction mode's UI.
     * This UI consists of the current layer the user
     * is building on, a preview of the different tiles
     * available on this layer and a rectangle highlighting
     * the currently selected tile.
     */
    public void renderUI() {
        int numTiles = game.getCurrentScene().getGameMap().getTiles().getNumberOfTiles();
        int tileUIScale = 4;
        int tileLength = SpriteSheet.tileSize * tileUIScale;
        for (int i = 0; i < numTiles; i++) {
            game.getRenderHandler().loadSprite(game.getCurrentScene().getBackgroundTileSheet().getSprite(i % numTiles, i / numTiles),
                                                camera.getX() + i * tileLength,
                                                camera.getY(), tileUIScale);
            if (i == tileID) {
                Rectangle currentTile = new Rectangle(camera.getX() + i * tileLength,
                                                    camera.getY(), SpriteSheet.tileSize, SpriteSheet.tileSize);
                currentTile.generateBorderGraphics(1, 0xFFFFFF);
                game.getRenderHandler().loadRectangle(currentTile, tileUIScale);
            }
        }
    }
    
}
