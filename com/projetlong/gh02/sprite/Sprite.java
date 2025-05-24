package com.projetlong.gh02.sprite;

import java.awt.image.BufferedImage;

public class Sprite {

    /** Array of pixels linked to the sprite. */
    private int[] pixels;

    /** Creates an instance of a sprite from a spritesheet
     * and the x and y position of the sprite on it.
     * @param spriteSheet The spritesheet where the sprite is located
     * @param xPos The x-coordinate of the sprite
     * @param yPos The y-coordinate of the sprite
     */
    public Sprite(SpriteSheet spriteSheet, int xPos, int yPos) {
        pixels = new int[SpriteSheet.tileSize * SpriteSheet.tileSize];
        pixels = spriteSheet.getSpriteSheet().getRGB(xPos, yPos, SpriteSheet.tileSize,
                            SpriteSheet.tileSize, pixels, 0, SpriteSheet.tileSize);
    }


    /** Creates an instance of a sprite from a singular
     * image containing the sprite.
     * @param sprite The sprite image
     */
    public Sprite(BufferedImage sprite) {
        pixels = new int[SpriteSheet.tileSize *  SpriteSheet.tileSize];
        pixels = sprite.getRGB(0, 0, SpriteSheet.tileSize,
                            SpriteSheet.tileSize, pixels, 0, SpriteSheet.tileSize);
    }


    /** Returns the pixel array of the sprite.
     * @return The pixel array of the sprite
     */
    public int[] getPixels() {
        try {
            return this.pixels;
        } catch (NullPointerException e) {
            System.out.println("The sprite doesn't have a pixel array.");
            return null;
        }
    }

}
