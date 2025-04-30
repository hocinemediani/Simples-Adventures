package com.projetlong.gh02;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.event.MouseInputListener;

public class MouseInputHandler implements MouseInputListener {

    /**  */
    private final GameFrame game;
    /**  */
    private final Rectangle camera;
    /**  */
    private File mapFile;
    /**  */
    private PrintWriter fileWriter;

    private int tileID = 0;

    /**  */
    public MouseInputHandler(GameFrame game) {
        this.game = game;
        this.camera = game.getRenderHandler().getCamera();
        this.mapFile = new File("./com/projetlong/gh02/testLevel.txt");
        try {
            fileWriter = new PrintWriter(new FileWriter(mapFile, true));
        } catch (IOException e) {
            System.out.println("Couldn't create the map file.");
        }
    }  


    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        int numTiles = game.getTiles().getNumberOfTiles();
        if (e.getButton() == 3) {
            tileID = (tileID + 1) % numTiles;
        }
        if (e.getButton() == 1) {
            int tileLength = SpriteSheet.tileSize * GameFrame.GLOBALSCALE;
            int xPos = ((int) e.getX() / (tileLength));
            int yPos = ((int) e.getY() / (tileLength));
            this.game.getRenderHandler().loadSprite(game.getBackgroundTileSheet().getSprite((tileID % numTiles), (tileID / numTiles)),
                                                    xPos * tileLength + this.camera.getX(),
                                                    yPos * tileLength + this.camera.getY(),
                                                    GameFrame.GLOBALSCALE);
            String mapString = tileID + "-" + xPos + "-" + yPos + "\n";
            fileWriter.write(mapString);
            fileWriter.flush();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        
    }


    @Override
    public void mouseExited(MouseEvent e) {
        
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
