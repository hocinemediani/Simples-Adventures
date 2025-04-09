package com.projetlong.gh02;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private int x, y;
    private final int speed = 5;
    private BufferedImage sprite;

    public Player() {
        x = 100;
        y = 100;
        try {
            sprite = ImageIO.read(getClass().getResource("assets/Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_UP]) y -= speed;
        if (keys[KeyEvent.VK_DOWN]) y += speed;
        if (keys[KeyEvent.VK_LEFT]) x -= speed;
        if (keys[KeyEvent.VK_RIGHT]) x += speed;
    }

    public void render(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, null);
        }
    }
}
