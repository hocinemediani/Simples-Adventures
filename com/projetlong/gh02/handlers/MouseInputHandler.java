package com.projetlong.gh02.handlers;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.entities.GameObject;
import com.projetlong.gh02.menus.MainMenu;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class MouseInputHandler implements MouseInputListener {

    /** The instance of game being played. */
    private final GameFrame game;

    /** Creates an instance of MouseInputHandler.
     * A mouse input handler handle's mouse input
     * and redirects it if necessary.
     * @param game The instance of game being played
     */
    public MouseInputHandler(GameFrame game) {
        this.game = game;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (this.game.getInputHandler().isInContructionMode()) {
            this.game.getSceneManager().getCurrentScene().getGameMap().getMapEditor().handleInput(e);
        } else {
            System.out.println("Click au coordonnées x :" + (e.getX() + game.getRenderHandler().getCamera().getX()) + " et y : " + (e.getY() + game.getRenderHandler().getCamera().getY()));
        }
        if (this.game.getSceneManager().getCurrentScene().getSceneID() == 0) {
            GameObject object = game.getSceneManager().getCurrentScene().getGameObjects().getFirst();
            if (object instanceof MainMenu menu) {
                menu.handleClick(e.getX(), e.getY());
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {}
    

    @Override
    public void mouseReleased(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}


    @Override
    public void mouseExited(MouseEvent e) {}


    @Override
    public void mouseDragged(MouseEvent e) {}


    @Override
    public void mouseMoved(MouseEvent e) {}

}
