package com.projetlong.gh02;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener, FocusListener {

    /**  */
    private final boolean[] keys = new boolean[128];
    /**  */
    private final GameFrame game;
    /**  */
    private boolean constructionMode = false;

    public InputHandler(GameFrame game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > 128) {
            System.out.println("Unsupported key");
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_0) {
            constructionMode = !constructionMode;
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


    /**  */
    public boolean movingUp() {
        return keys[KeyEvent.VK_Z] || keys[KeyEvent.VK_UP];
    }
    

    /**  */
    public boolean movingDown() {
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    }
    

    /**  */
    public boolean movingLeft() {
        return keys[KeyEvent.VK_Q] || keys[KeyEvent.VK_LEFT];
    }
    

    /**  */
    public boolean movingRight() {
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    /**  */
    public boolean isInContructionMode() {
        return this.constructionMode;
    }


    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
