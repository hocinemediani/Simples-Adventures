package com.projetlong.gh02;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class MouseInputHandler implements MouseInputListener {

    /**  */
    private final GameFrame game;

    /**  */
    public MouseInputHandler(GameFrame game) {
        this.game = game;
    }  


    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (this.game.getInputHandler().isInContructionMode()) {
            this.game.getCurrentScene().getGameMap().getMapEditor().handleInput(e);
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
