package com.projetlong.gh02.entities;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.handlers.InputHandler;
import com.projetlong.gh02.handlers.RenderHandler;
import com.projetlong.gh02.scene.Scene;
import com.projetlong.gh02.sprite.Sprite;
import com.projetlong.gh02.sprite.SpriteSheet;
import java.awt.Color;
import java.awt.Graphics;

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

    private final GameFrame game;

    private final String name;

    /** Creates an instance of player.
     * A player has a sprite and a camera that moves along
     * with him. He is controlled by the ZQSD or arrow keys.
     * @param playerSpriteSheet The player's sprite sheet
     * @param inputHandler The input handler used to detect movement
     * @param camera The player's camera
     */
    public Player(SpriteSheet playerSpriteSheet, InputHandler inputHandler, Rectangle camera, Scene scene, GameFrame game, String name) {
        this.name = name;
        this.game = game;
        this.playerSpriteSheet = playerSpriteSheet;
        this.sprite = playerSpriteSheet.getSprite(0, 0);
        this.camera = camera;
        this.inputHandler = inputHandler;
        this.xPos = camera.getWidth() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE - 1);
        this.yPos = camera.getHeight() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE);
        this.playerRectangle = new Rectangle(xPos + SpriteSheet.tileSize / 2, yPos + SpriteSheet.tileSize / 2,
                                            SpriteSheet.tileSize / 2, SpriteSheet.tileSize / 2);
        playerRectangle.generateBorderGraphics(1, 0x194875);
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        renderHandler.loadSprite(this.sprite, xPos, yPos, scale);
        renderHandler.loadRectangle(playerRectangle, scale);
        Graphics graphics = renderHandler.getViewGraphics();
        renderHandler.drawText(this.name, this.playerRectangle.getX(), this.yPos + 200, 6* scale, Color.WHITE, graphics);
    }


    @Override
    public void update(GameFrame game) {
        int tileLength = SpriteSheet.tileSize * GameFrame.GLOBALSCALE;
        int x = xPos / tileLength;
        int y = yPos / tileLength;
        if (inputHandler.movingUp()) {
            setPlayerSprite(playerSpriteSheet.getSprite(1, 0));
            if (game.getSceneManager().getCurrentScene().getGameMap().getMaxLayerAtPosition(x + speed, y - speed/tileLength) != 1) {
                playerRectangle.moveY(-speed);
                camera.moveY(-cameraSpeed);
                this.yPos -= speed;
            }
        }
        if (inputHandler.movingDown()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0,0));
            if (game.getSceneManager().getCurrentScene().getGameMap().getMaxLayerAtPosition(x + speed, y + speed/tileLength + 1) != 1) {
                playerRectangle.moveY(speed);
                camera.moveY(cameraSpeed);
                this.yPos += speed;
            }
        }
        if (inputHandler.movingLeft()) {
            setPlayerSprite(playerSpriteSheet.getSprite(2, 0));
            if (game.getSceneManager().getCurrentScene().getGameMap().getMaxLayerAtPosition(x - speed/tileLength, y) != 1) {
                playerRectangle.moveX(-speed);
                camera.moveX(-cameraSpeed);
                this.xPos -= speed;
            }
        }
        if (inputHandler.movingRight()) {
            setPlayerSprite(playerSpriteSheet.getSprite(0, 0));
            if (game.getSceneManager().getCurrentScene().getGameMap().getMaxLayerAtPosition(x + speed/tileLength + 1, y) != 1) {
                playerRectangle.moveX(speed);
                camera.moveX(cameraSpeed);
                this.xPos += speed;
            }
        }

        /* Updates the camera position to be centered at each frame. */
        camera.setX(xPos - camera.getWidth() / 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE / 2);
        camera.setY(yPos - camera.getHeight() / 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE / 2);

        for (GameObject obj : game.getSceneManager().getCurrentScene().getGameObjects()) {
            if (obj instanceof NPC npc) {
                Rectangle interactionZone = npc.getNPRectangle();
                if (this.isInRectangle(interactionZone) ) {
                    npc.setcanInteract(true);
                } else {
                    npc.setcanInteract(false);
                }
            }
        }
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


    public boolean isInRectangle(Rectangle rectangle) {
    return xPos >= rectangle.getX() - rectangle.getWidth() && xPos <= rectangle.getX() + rectangle.getWidth() * 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE &&
           yPos >= rectangle.getY() - rectangle.getHeight() && yPos <= rectangle.getY() + rectangle.getHeight() * 2 + SpriteSheet.tileSize * GameFrame.GLOBALSCALE;
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
