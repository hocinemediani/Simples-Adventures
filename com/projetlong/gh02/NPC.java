package com.projetlong.gh02;

import java.util.Random;

public class NPC implements GameObject {

    /** NPC's cadre */
    private final Rectangle nPCRectangle;

    /** x-position */
    private int xPos;

    /** y-position */
    private int yPos;

    /** The NPC's sprite sheet */
    private final SpriteSheet nPCSpriteSheet;

    /** The NPC's current sprite */
    private Sprite sprite;

    /** The speed of NPCs */
    private final int speed = GameFrame.GLOBALSCALE;

    /** NPC Class attributes used to change the behavior of NPCs */
    private static int i = 0;   // time adjustment
    private static int j = 0;   // behavior adjustment (random value between 0 and 3)



    public NPC(SpriteSheet npcSpriteSheet, int xPos_initial, int yPos_initial) {
        this.xPos = xPos_initial;
        this.yPos = yPos_initial;
        this.nPCSpriteSheet = npcSpriteSheet;
        this.nPCRectangle = new Rectangle(this.xPos, this.yPos, SpriteSheet.tileSize, SpriteSheet.tileSize);
        this.sprite = npcSpriteSheet.getSprite(0, 0);
        nPCRectangle.generateBorderGraphics(1, 0x194875);
        
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        renderHandler.loadSprite(this.sprite, this.xPos, this.yPos, scale);
        renderHandler.loadRectangle(this.nPCRectangle, scale);
    }

    @Override
    public void update(GameFrame game) {
        if (i == 30) {   // change j randomly after 30 frame
            
            Random rand = new Random();
            i = 0;
            j = rand.nextInt(4);
        }

        i++;
    

        if (j == 0) {
            transform(0, -speed, 0);
            this.sprite = nPCSpriteSheet.getSprite(1, 0);
        } else if (j == 1) {
            transform(0, speed, 0);
            this.sprite = nPCSpriteSheet.getSprite(0, 0);
        } else if (j == 2) {
            transform(speed, 0, 0);
            this.sprite = nPCSpriteSheet.getSprite(0, 0);
        } else {
            transform(-speed, 0, 0);
            this.sprite = nPCSpriteSheet.getSprite(2, 0);
        }

        
        
    }

    @Override
    public void transform(int dx, int dy, int dTheta) {
        this.xPos += dx;
        this.yPos += dy;
        this.nPCRectangle.moveX(dx);
        this.nPCRectangle.moveY(dy); 
    }


}
