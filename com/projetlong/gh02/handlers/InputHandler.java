package com.projetlong.gh02.handlers;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.projetlong.gh02.GameFrame;

public class InputHandler implements KeyListener, FocusListener {

    /** The array of currently pressed keys. */
    private final boolean[] keys = new boolean[128];
    /** Indicates if the user is in construction mode. */
    private boolean isInConstructionMode = false;
    /** The instance of game being played. */
    private final GameFrame game;

    /** Creates an instance of InputHandler.
     * An input handler gets input from the keyboard
     * and maps it to certain actions.
     * @param game The instance of game being played    
     */
    public InputHandler(GameFrame game) {
        this.game = game;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > 128) {
            System.out.println("Unsupported key");
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            isInConstructionMode = !isInConstructionMode;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            game.getSceneLoader().loadScene(game.getSceneManager().getCurrentScene().getSceneID() + 1);
        }
        // Gestion du layer en mode construction
        if (isInConstructionMode) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                game.getSceneManager().getCurrentScene().getGameMap().getMapEditor().nextLayer();
            }
            if (e.getKeyCode() == KeyEvent.VK_X) {
                game.getSceneManager().getCurrentScene().getGameMap().getMapEditor().previousLayer();
            }
        }
        keys[e.getKeyCode()] = true;
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() > 128) {
            System.out.println("Unsupported key");
            return;
        }
        keys[e.getKeyCode()] = false;
    }


    @Override
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
        }
    }


    /** Returns true if the user press a
     * key that is mapped to move the character up.
     * @return If the player is moving up
     */
    public boolean movingUp() {
        return keys[KeyEvent.VK_Z] || keys[KeyEvent.VK_UP];
    }
    

    /** Returns true if the user press a
     * key that is mapped to move the character down.
     * @return If the player is moving down
     */
    public boolean movingDown() {
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    }
    

    /** Returns true if the user press a
     * key that is mapped to move the character left.
     * @return If the player is moving left
     */
    public boolean movingLeft() {
        return keys[KeyEvent.VK_Q] || keys[KeyEvent.VK_LEFT];
    }
    

    /** Returns true if the user press a
     * key that is mapped to move the character right.
     * @return If the player is moving right
     */
    public boolean movingRight() {
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    /** Returns true if the player is in
     * construction mode.
     * @return If the players is in construction mode
     */
    public boolean isInContructionMode() {
        return this.isInConstructionMode;
    }


    @Override
    public void focusGained(FocusEvent e) {}


    @Override
    public void keyTyped(KeyEvent e) {}

}
