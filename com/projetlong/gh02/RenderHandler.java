package com.projetlong.gh02;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    private final BufferedImage view;
    private final int[] pixels;

    /** Creates the view as a BufferedImage with set
     * width and height and also the pixels array linked
     * to it.
     */
    public RenderHandler(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /* Creates the pixels array linked to our view */
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    }


    /** Renders the modified BufferedImage to the screen. */
    public void render(Graphics graphics) {
        for (int index = 0; index < pixels.length; index++) {
            pixels[index] = 0xFFFF;
        }

        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }
}