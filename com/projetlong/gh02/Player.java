package com.projetlong.gh02;

public class Player implements GameObject {

    /** The player's hitbox. */
    private final Rectangle playerRectangle;
    /** The input handler to detect movement. */
    private final InputHandler inputHandler;
    /** The camera linked to the player. */
    private final Rectangle camera;
    /** The player's x-position. */
    private int xPos;
    /** The player's y-position. */
    private int yPos;
    /** The player's sprite sheet. */
    private final SpriteSheet playerSpriteSheet;
    /** The player's current sprite. */
    private Sprite sprite;
    /** The player's movement speed. */
    private final int speed = GameFrame.GLOBALSCALE;
    /** The camera's movement speed. */
    private final int cameraSpeed = GameFrame.GLOBALSCALE;

    /** Creates an instance of player.
     * A player has a sprite and a camera that moves along
     * with him. He is controlled by the ZQSD or arrow keys.
     * @param playerSpriteSheet The player's sprite sheet
     * @param inputHandler The input handler used to detect movement
     * @param camera The player's camera
     */
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
            camera.moveY(-cameraSpeed);
            this.yPos -= speed;
        }
        if (inputHandler.movingDown()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0,0));
            playerRectangle.moveY(speed);
            camera.moveY(cameraSpeed);
            this.yPos += speed;
        }
        if (inputHandler.movingLeft()) {
            setPlayerSprite(playerSpriteSheet.getSprite(2, 0));
            playerRectangle.moveX(-speed);
            camera.moveX(-cameraSpeed);
            this.xPos -= speed;
        }
        if (inputHandler.movingRight()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0, 0));
            playerRectangle.moveX(speed);
            camera.moveX(cameraSpeed);
            this.xPos += speed;
        }

        /* Updates the camera position to be centered at each frame. */
        camera.setX(xPos - camera.getWidth() / 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE / 2);
        camera.setY(yPos - camera.getHeight() / 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE / 2);
    }

    
    @Override
    public void transform(int dx, int dy, int dTheta) {
        this.xPos += dx;
        this.yPos += dy;
        this.playerRectangle.moveX(dx);
        this.playerRectangle.moveY(dy);
        this.camera.moveX(dx);
        this.camera.moveY(dy);
    }


    /** Set the current player's sprite to the
     * specified sprite.
     * @param sprite The new player's current sprite
     */
    public void setPlayerSprite(Sprite sprite) {
        this.sprite = sprite;
    }


    /** Returns the player's sprite sheet.
     * @return The player's sprite sheet
     */
    public SpriteSheet getSpriteSheet() {
        return this.playerSpriteSheet;
    }

}
