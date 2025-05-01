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
        this.mapFile = new File("./com/projetlong/gh02/testLevel2.txt");
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
        int tileLength = SpriteSheet.tileSize * GameFrame.GLOBALSCALE;
        int xPos = (((int) e.getX() + this.camera.getX()) / (tileLength));
        int yPos = (((int) e.getY() + this.camera.getY()) / (tileLength));
        if (e.getX() + this.camera.getX() < 0) {
            xPos = (((int) e.getX() + this.camera.getX() - tileLength) / (tileLength));
        }
        if (e.getY() + this.camera.getY() < 0) {
            yPos = (((int) e.getY() + this.camera.getY() - tileLength) / (tileLength));
        }
        if (e.getButton() == 3) {
            tileID = (tileID + 1) % numTiles;
        }
        if (e.getButton() == 1) {
            this.game.getGameMap().addMappedTile(tileID, xPos, yPos);
            String mapString = tileID + "," + xPos  + "," + yPos + "\n";
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
