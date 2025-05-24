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
        }
        if (this.game.getSceneManager().getCurrentScene().getSceneID() == 0) {
            GameObject object = game.getSceneManager().getCurrentScene().getGameObjects().getFirst();
            if (object instanceof MainMenu menu) {
                menu.handleClick(e.getX(), e.getY(), game);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.game.getInMenu()) {
                    int x = e.getX();
                    int y = e.getY();
                    int x1 = (int) (this.game.getWidth()* 0.7);
                    int x2 = (int) (this.game.getWidth()* 0.7 + this.game.getmenuBackround().getWidth() * 0.5);
                    int y1 = (int) (this.game.getHeight() * 0.5);
                    int y2 = (int) (this.game.getHeight() * 0.5 + this.game.getmenuBackround().getHeight() * 0.5);
                    if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                        if (!this.game.getplayerName().trim().isEmpty()) {

                            this.game.setEtatMenu(false);
                            this.game.setisNameEntered(true);
                            this.game.setisInPrincipalPage(true);
                            System.out.println("Starting game for: " + this.game.getplayerName());
                        }
                    }
                } else if (this.game.getisInPrincipalPage()) {
                    /* Principal page processing */
                    int x = e.getX();
                    int y = e.getY();
                    int x1 = (int) (this.game.getWidth()* 0.29);
                    int x2 = (int) (this.game.getWidth()* 0.29 + this.game.getmenuBackround().getWidth());
                    int y11 = (int) (this.game.getHeight() * 0.55);
                    int y12 = (int) (this.game.getHeight() * 0.55 + this.game.getmenuBackround().getHeight() * 0.20);

                    int y21 = (int) (this.game.getHeight() * 0.33);
                    int y22 = (int) (this.game.getHeight() * 0.33 + this.game.getmenuBackround().getHeight() * 0.20);
                    if (x >= x1 && x <= x2 && y >= y21 && y <= y22) {
                        this.game.setisInPrincipalPage(false);
                        this.game.setIsNewGame(true);
                        System.out.println("Starting a new game for: " + this.game.getplayerName());
                    } else if (x >= x1 && x <= x2 && y >= y11 && y <= y12) {
                        
                        this.game.setIsInSetting(true);
                        System.out.println("Opening settings for: " + this.game.getplayerName());
                    }
                }

    }
    

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
