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
            int[] tileInfo = new int[3];
            tileInfo[0] = tileID;
            tileInfo[1] = xPos;
            tileInfo[2] = yPos;
            this.game.getCurrentScene().getGameMap().addMappedTile(tileInfo);
            String mapString = xPos  + "," + yPos;
            this.game.getCurrentScene().getGameMap().writeToFile(mapString, tileID);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            // delete the tile from the array of mappedtiles
            // delete the tile from the map file
            this.game.getCurrentScene().getGameMap().deleteMappedTile(xPos, yPos);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            tileID = (tileID + 1) % numTiles;
        }
    }


    /**  */
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
