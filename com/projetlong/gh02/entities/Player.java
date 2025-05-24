package com.projetlong.gh02.entities;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.Sprite;
import com.projetlong.gh02.SpriteSheet;
import com.projetlong.gh02.handlers.InputHandler;
import com.projetlong.gh02.handlers.RenderHandler;
import com.projetlong.gh02.scene.Scene;
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


    private Scene currentScene;

    private final GameFrame game;

    /** Creates an instance of player.
     * A player has a sprite and a camera that moves along
     * with him. He is controlled by the ZQSD or arrow keys.
     * @param playerSpriteSheet The player's sprite sheet
     * @param inputHandler The input handler used to detect movement
     * @param camera The player's camera
     */
    public Player(SpriteSheet playerSpriteSheet, InputHandler inputHandler, Rectangle camera, Scene scene, GameFrame game) {
        this.game = game;
        this.playerSpriteSheet = playerSpriteSheet;
        this.sprite = playerSpriteSheet.getSprite(0, 0);
        this.camera = camera;
        this.inputHandler = inputHandler;
        this.xPos = camera.getWidth() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE - 1);
        this.yPos = camera.getHeight() / 2 - SpriteSheet.tileSize * (GameFrame.GLOBALSCALE);
        this.playerRectangle = new Rectangle(xPos, yPos,
                                            SpriteSheet.tileSize, SpriteSheet.tileSize);
        playerRectangle.generateBorderGraphics(1, 0x194875);
        this.currentScene = scene;

    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        renderHandler.loadSprite(this.sprite, xPos, yPos, scale);
        renderHandler.loadRectangle(playerRectangle, scale);
        // graphics est le milieu où on va dessiner
        Graphics graphics = renderHandler.getViewGraphics();
        renderHandler.drawText(this.game.getplayerName(), this.playerRectangle.getX(), this.yPos + 200, 6* scale, Color.WHITE, graphics);
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

        for (GameObject obj : currentScene.getGameObjects()) {
            if (obj instanceof NPC npc) {
                Rectangle interactionZone = npc.getNPRectangle();
                //Rectangle playerHitbox = new Rectangle(xPos, yPos, width, height); // ou ta vraie hitbox
                //npc.setcanInteract(false);
                

                if (this.isInRectangle(interactionZone) ) {
                    // if (game.isInteractionKeyPressed()) {
                    //     // Lancer l’interaction
                    //     System.out.println("Interagis avec NPC !");
                    // }
                    System.out.println("Interagis avec NPC !");
                    npc.setcanInteract(true);
                } else {
                    npc.setcanInteract(false);
                }
                //npc.setcanInteract(false);


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
    return xPos >= rectangle.getX() - 20 && xPos <= rectangle.getX() + 2.5 * rectangle.getWidth() &&
           yPos >= rectangle.getY() - 20 && yPos <= rectangle.getY() + 2.5*rectangle.getHeight();
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
