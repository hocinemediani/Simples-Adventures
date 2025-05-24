package com.projetlong.gh02;

public class Rectangle {

    /** The starting x-coordinate of the rectangle. */
    private int xPos;
    /** The starting y-coordinate of the rectangle. */
    private int yPos;
    /** The width of the rectangle. */
    private int width;
    /** The height of the rectangle. */
    private int height;
    /** The pixels array of the rectangle. */
    private int[] pixels = null;

    /** Creates a new Rectangle with specified
     * initial x-position, y-position, width and
     * height.
     * @param x The top-left hand corner x-coordinate
     * @param y The top-left hand corner y-coordinate
     * @param w The width of the rectangle
     * @param h The height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        this.xPos = x;
        this.yPos = y;
        this.width = w;
        this.height = h;
    }


    /** Creates a new rectangle of size 0,
     * on the top left hand corner of the
     * screen.
     */
    public Rectangle(){
        this(0, 0, 0, 0);
    }


    /** Initializes the pixels array linked to
     * the rectangle at a given color. The result
     * is a rectangle with a solid color.
     * @param color The color it initializes to
     */
    public void generateGraphics(int color) {
        pixels = new int[this.width * this.height];

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = GameFrame.ALPHA;
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[x + y * this.width] = color; 
            }
        }
    }


    /** Initializes the pixels array linked to
     * the rectangle to a solid border at a given
     * color and given border width.
     * @param borderWidth The width of the border
     * @param color The color of the border
     */
    public void generateBorderGraphics(int borderWidth, int color) {
        if (pixels == null) {
            pixels = new int[this.width * this.height];
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = GameFrame.ALPHA;
            }
        }

        for (int y = 0; y < borderWidth; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[x + y * this.width] = color;
            }
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < borderWidth; x++) {
                pixels[x + y * this.width] = color;
            }
            for (int x = this.width - borderWidth ; x < this.width; x++) {
                pixels[x + y * this.width] = color;
            }
        }

        for (int y = this.height - borderWidth; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[x + y * this.width] = color;
            }
        }
    }


    /** Returns the pixels array of the rectangle.
     * Returns null if the rectangle didn't get initialized
     * to any sort of graphics.
     * @return The pixels array of the rectangle
     */
    public int[] getPixels() {
        return this.pixels;
    }


    /** Returns the x-position of the rectangle.
     * @return The x-position of the rectangle
     */
    public int getX() {
        return this.xPos;
    }


    /** Returns the y-position of the rectangle.
     * @return The y-position of the rectangle
     */
    public int getY() {
        return this.yPos;
    }


    /** Returns the width of the rectangle.
     * @return The width of the rectangle
     */
    public int getWidth() {
        return this.width;
    }


    /** Returns the height of the rectangle.
     * @return The height of the rectangle
     */
    public int getHeight() {
        return this.height;
    }


    /** Shifts the x-position of the rectangle
     * of dx.
     * @param dx The new x-position of the rectangle
     */
    public void moveX(int dx) {
        this.xPos += dx;
    }


    /** Shifts the y-position of the rectangle
     * of dy.
     * @param newYPos The new y-position of the rectangle
     */
    public void moveY(int dy) {
        this.yPos += dy;
    }


    /** Sets the width of the rectangle to
     * the specified width.
     * @param newWidth The new width of the rectangle
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }


    /** Sets the height of the rectangle to
     * the specified height.
     * @param newHeight The new height of the rectangle
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }


    /** Sets the x-coordinate of the rectangle
     * to the specified x-coordinate.
     * @param xPos The new x-coordinate of the rectangle
     */
    public void setX(int xPos) {
        this.xPos = xPos;
    }


    /** Sets the y-coordinate of the rectangle
     * to the specified y-coordinate.
     * @param xPos The new y-coordinate of the rectangle
     */
    public void setY(int yPos) {
        this.yPos = yPos;
    }


    public boolean isClicked(int x, int y) {
        return x >= xPos && x < xPos + width && y >= yPos && y < yPos + height;
    }

}
