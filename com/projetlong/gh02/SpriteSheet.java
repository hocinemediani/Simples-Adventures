package com.projetlong.gh02;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    /** The array of pixels of the spritesheet */
    private int[] pixels;
    /** An array of all the sprites in order. */
    private Sprite[] spritesArray = null;
    /** The spritesheet's image. */
    private final BufferedImage spriteSheet;
    /** The image's width. */
    public final int imageWidth;
    /** The image's height. */
    public final int imageHeight;
    /** The spritesheet's tilesize. */
    public static int tileSize = 16;

    /** Creates an instance of a spritesheet from an
     * image and loads the spriteArray with the sprites.
     * @param spriteSheet The spritesheet's image
     */
    public SpriteSheet(BufferedImage spriteSheet) {
        if (spriteSheet == null) {
            System.out.println("The sprite sheet passed as argument is null.");
            this.spriteSheet = null;
            this.imageWidth = 0;
            this.imageHeight = 0;
            return;
        }
        this.spriteSheet = spriteSheet;
        this.imageWidth = spriteSheet.getWidth();
        this.imageHeight = spriteSheet.getHeight();
        this.pixels = new int[this.imageHeight * this.imageWidth];
        pixels = spriteSheet.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        this.loadSprites();
    }


    /** Loads all of the sprites in the sprite array. */
    public final void loadSprites() {
        this.spritesArray = new Sprite[(imageWidth / tileSize) * (imageHeight / tileSize)];
        for (int y = 0; y < imageHeight; y += tileSize) {
            for (int x = 0; x < imageWidth; x += tileSize) {
                spritesArray[(y / tileSize) * (imageWidth / tileSize) + (x / tileSize)] = new Sprite(this, x, y);
            }
        }
    }


    /** Returns the sprite at positon (xPos, yPos) when
     * viewing the spritesheet's image as an array of 
     * sprites. Returns null if the spritesArray hasn't
     * been initialized.
     * @param xPos The x-coordinate of the sprite
     * @param yPos The y-coordinate of the sprite
     * @return The sprite at coordinates (xPos, yPos)
     */
    public Sprite getSprite(int xPos, int yPos) {
        if (this.spritesArray == null) {
            return null;
        }
        try {
            return spritesArray[xPos + yPos * (this.imageWidth / tileSize)];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No sprite at position (" + xPos + ", " + yPos + ")");
            return null;
        }
    }


    /** Returns the pixels array of the spritesheet.
     * @return The pixels array of the spritesheet
     * */
    public int[] getPixels() {
        return this.pixels;
    }


    /** Returns the spritesheet's image.
     * @return The spritesheet's image
     */
    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }


    public int getWidth() {
        return this.imageWidth;
    }

    public int getHeight() {
        return this.imageHeight;
    }

}
