package com.projetlong.gh02;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    /* Our working viewport */
    private final BufferedImage view;
    /* The viewport's related pixels array */
    private final int[] pixels;

    /** Creates the view as a BufferedImage with set
     * width and height and also the pixels array linked
     * to it.
     * @param width The width of the viewport
     * @param height The height of the viewport
     */
    public RenderHandler(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /* Creates the pixels array linked to our view */
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    }


    /** Renders the pixels array to the screen.
     * @param graphics The supplied graphics to draw onto
    */
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    
    /** Renders a BufferedImage to the screen, with origin
     * xPos and yPos.
     * @param image The image to render
     * @param xPos The starting x-coordinate of the render
     * @param yPos The starting y-coordinate of the render
     */
    public void renderImage(BufferedImage image, int xPos, int yPos) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                pixels[(y + yPos) * view.getWidth() + xPos + x] = imagePixels[x + y * image.getWidth()];
            }
        }
    }
}