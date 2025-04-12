package com.projetlong.gh02;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    /* Our working viewport. */
    private final BufferedImage view;
    /* The viewport's related pixels array. */
    private final int[] pixels;

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
        
        for (int yScreen = 0; yScreen < image.getHeight(); yScreen++) {
            for (int xScreen = 0; xScreen < image.getWidth(); xScreen++) {
                for (int yScale = 0; yScale < scale; yScale++) {
                    for (int xScale = 0; xScale < scale; xScale++) {
                        setPixelColor(imagePixels[xScreen + yScreen * image.getWidth()], (xPos + xScreen * scale + xScale), ((yScreen * scale) + yPos + yScale));
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
    private void setPixelColor(int pixelColor, int xPos, int yPos) {
        int pixelIndex = xPos + yPos * view.getWidth();
        if (pixels.length > pixelIndex) {
            pixels[pixelIndex] = pixelColor;
        }
    }
}