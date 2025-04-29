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
    private final SpriteSheet playerSpriteSheet;
    /**  */
    private Sprite sprite;
    /** */
    private final int speed = 3;

    /**  */
    public Player(SpriteSheet playerSpriteSheet, InputHandler inputHandler, Rectangle camera) {
        this.playerSpriteSheet = playerSpriteSheet;
        this.sprite = playerSpriteSheet.getSprite(0, 0);
        this.camera = camera;
        this.inputHandler = inputHandler;
        this.xPos = camera.getWidth() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE - 1);
        this.yPos = camera.getHeight() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE);
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
            setPlayerSprite(playerSpriteSheet.getSprite(1, 0));
            playerRectangle.moveY(-speed);
            camera.moveY(-speed);
            this.yPos -= speed;
        }

        if (inputHandler.movingDown()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0,0));
            playerRectangle.moveY(speed);
            camera.moveY(speed);
            this.yPos += speed;
        }

        if (inputHandler.movingLeft()) {
            setPlayerSprite(playerSpriteSheet.getSprite(2, 0));
            playerRectangle.moveX(-speed);
            camera.moveX(-speed);
            this.xPos -= speed;
        }

        if (inputHandler.movingRight()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0, 0));
            playerRectangle.moveX(speed);
            camera.moveX(speed);
            this.xPos += speed;
        }
    }


    /**  */
    public SpriteSheet getSpriteSheet() {
        return this.playerSpriteSheet;
    }


    /** */
    public void setPlayerSprite(Sprite sprite) {
        this.sprite = sprite;
    }


    @Override
    public void transform(int dx, int dy, int dTheta) {
        this.xPos += dx;
        this.yPos += dy;
    }


    @Override
    public void delete() {}

}
