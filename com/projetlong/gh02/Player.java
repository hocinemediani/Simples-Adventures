package com.projetlong.gh02;

public class Player implements GameObject {

    /**  */
    private final Rectangle playerRectangle;
    /**  */
    private final InputHandler inputHandler;
    /**  */
    private final Rectangle camera;
    /** The player's x-position. */
    private int xPos;
    /** The player's y-position. */
    private int yPos;
    /**  */
    private final Sprite sprite;
    /** */
    private final int speed = 5;

    /**  */
    public Player(Sprite sprite, InputHandler inputHandler, Rectangle camera) {
        this.sprite = sprite;
        this.camera = camera;
        this.inputHandler = inputHandler;
        this.xPos = 700;
        this.yPos = 500;
        this.playerRectangle = new Rectangle(xPos, yPos,
                        SpriteSheet.tileSize, SpriteSheet.tileSize);
        playerRectangle.generateBorderGraphics(1, 0x194875);
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        renderHandler.loadSprite(this.sprite, xPos, yPos, scale);
        renderHandler.loadRectangle(playerRectangle, scale);
    }


    @Override
    public void update(GameFrame game) {

        if (inputHandler.movingUp()) {
            playerRectangle.moveY(-speed);
            camera.moveY(-speed);
            this.yPos -= speed;
        }

        if (inputHandler.movingDown()) {
            playerRectangle.moveY(speed);
            camera.moveY(speed);
            this.yPos += speed;
        }

        if (inputHandler.movingLeft()) {
            playerRectangle.moveX(-speed);
            camera.moveX(-speed);
            this.xPos -= speed;
        }

        if (inputHandler.movingRight()) {
            playerRectangle.moveX(speed);
            camera.moveX(speed);
            this.xPos += speed;
        }
    }


    @Override
    public void transform(int dx, int dy, int dTheta) {
        this.xPos += dx;
        this.yPos += dy;
    }


    @Override
    public void delete() {}

}
