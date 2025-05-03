package com.projetlong.gh02;

import java.awt.event.MouseEvent;

public class MapEditor {

    /**  */
    private final GameFrame game;
    /**  */
    private final Rectangle camera;
    /**  */
    private int tileID;

    /**  */
    public MapEditor(GameFrame game) {
        this.game = game;
        this.camera = this.game.getRenderHandler().getCamera();
    }

    /**  */
    public void handleInput(MouseEvent e) {
        int numTiles = game.getTiles().getNumberOfTiles();
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
            this.game.getGameMap().addMappedTile(tileID, xPos, yPos);
            String mapString = xPos  + "," + yPos;
            this.game.getGameMap().writeToFile(mapString, tileID);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            // delete the tile from the array of mappedtiles
            // delete the tile from the map file
            this.game.getGameMap().deleteMappedTile(xPos, yPos);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            tileID = (tileID + 1) % numTiles;
        }
    }


    /**  */
    public void renderUI() {
        int numTiles = game.getGameMap().getTiles().getNumberOfTiles();
        int tileUIScale = 4;
        int tileLength = SpriteSheet.tileSize * tileUIScale;
        for (int i = 0; i < numTiles; i++) {
            game.getRenderHandler().loadSprite(game.getBackgroundTileSheet().getSprite(i % numTiles, i / numTiles),
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
