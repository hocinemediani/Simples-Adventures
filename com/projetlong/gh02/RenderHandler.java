package com.projetlong.gh02;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    /** Our working viewport. */
    private final BufferedImage view;
    /** The viewport's related pixels array. */
    private final int[] pixels;
    /** Represents the camera. */
    private final Rectangle camera;

    /** Creates the view as a BufferedImage with set
     * width and height and also the pixels array linked
     * to it.
     * @param width The width of the viewport
     * @param height The height of the viewport
     */
    public RenderHandler(int width, int height) {
        /* Initializes the view as a BufferedImage. */
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /* Creates the pixels array linked to the view. */
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

        /* Initializes the camera. */
        camera = new Rectangle(0, 0, width, height);
    }


    /** Renders the pixels array to the screen.
     * @param graphics The supplied graphics to draw onto
    */
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    
    /** Modifies the BufferedImage pixels array to load image
     * data at coordinates xPos and yPos. The loaded data will
     * be rendered upon calling the render method.
     * @param image The image to render
     * @param xPos The starting x-coordinate of the render
     * @param yPos The starting y-coordinate of the render
     * @param scale The desired scaling ratio
     */
    public void loadImageData(BufferedImage image, int xPos, int yPos, int scale) {
        /* Creates the pixels array linked to the image. */
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        loadFromArray(imagePixels, image.getHeight(), image.getWidth(), xPos, yPos, scale);
    }
    

    /** Loads rectangle data into the rendering buffers at the
     * desired scaling ratio. The loaded data will be rendered
     * upon calling the render method.
     * @param rectangle The rectangle to render
     * @param scale The desired scaling ratio
     */
    public void loadRectangle(Rectangle rectangle, int scale) {
        int[] rectanglePixels = rectangle.getPixels();
        if (rectanglePixels == null) {
            return;
        }
        loadFromArray(rectanglePixels, rectangle.getHeight(), rectangle.getWidth(),
        rectangle.getX(), rectangle.getY(), scale);
    }


    /** Loads sprite data into the rendering buffers at the
     * desired scaling ratio. The loaded data will be rendered
     * upon calling the render method.
     * @param sprite The sprite to render
     * @param xPos The starting x-position
     * @param yPos The starting y-position,
     * @param scale The desired scaling ratio
     */
    public void loadSprite(Sprite sprite, int xPos, int yPos, int scale) {
        if (sprite == null) {
            return;
        }
        
        int[] spritePixels = sprite.getPixels();

        try {
            loadFromArray(spritePixels, SpriteSheet.tileSize, SpriteSheet.tileSize, xPos, yPos, scale);
        } catch (NullPointerException e) {
            System.out.println("Couldn't load sprite.");
        }
    }


    /** Helper to load image data from an array of pixels.
     * @param pixelArray The array of pixels
     * @param renderHeight The initial image's height
     * @param renderWidth The initial image's width
     * @param xPos The starting x-position
     * @param yPos The starting y-position
     * @param scale The desired scaling ratio
     */
    public void loadFromArray(int[]pixelArray, int renderHeight, int renderWidth,
                                int xPos, int yPos, int scale) {
        for (int yScreen = 0; yScreen < renderHeight; yScreen++) {
            for (int xScreen = 0; xScreen < renderWidth; xScreen++) {
                for (int yScale = 0; yScale < scale; yScale++) {
                    for (int xScale = 0; xScale < scale; xScale++) {
                        setPixelColor(pixelArray[xScreen + yScreen * renderWidth],
                        (xPos + xScreen * scale + xScale), ((yScreen * scale) + yPos + yScale));
                    }
                }
            }
        }
    }


    /** Sets the pixel's color at coordinates (xPos, yPos)
     * to pixelColor.
     * @param pixelColor The desired color for the pixel
     * @param xPos The x-coordinate of the pixel to change
     * @param yPos The y-coordinate of the pixel to change
     */
    public void setPixelColor(int pixelColor, int xPos, int yPos) {
        if (isPixelOutOfBounds(xPos, yPos)) {
            return;
        }
        if ((pixelColor & 0xFFFFFF) == GameFrame.ALPHA) {
            return;
        }
        int pixelIndex = (xPos - camera.getX()) + (yPos - camera.getY()) * view.getWidth();
        pixels[pixelIndex] = pixelColor;
    }


    /** Verifies if a pixel is out of bounds in comparison to
     * the position of the camera.
     * @param xPos The x-position of the pixel
     * @param yPos The y-position of the pixel
     * @return If the pixel is out of bounds or not
    */
    public boolean isPixelOutOfBounds(int xPos, int yPos) {
        return !(xPos >= camera.getX()) || !(yPos >= camera.getY()) ||
               !(xPos < camera.getX() + camera.getWidth()) ||
               !(yPos < camera.getY() + camera.getHeight());
    }


    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }


    /** Returns the camera.
     * @return The camera
    */
    public Rectangle getCamera() {
        return this.camera;
    }
}
