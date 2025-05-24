package com.projetlong.gh02.entities;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.Sprite;
import com.projetlong.gh02.SpriteSheet;
import com.projetlong.gh02.handlers.RenderHandler;

import java.awt.Color;
import java.awt.Graphics;

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

    //
    private boolean canInteract = false;



    public NPC(SpriteSheet npcSpriteSheet, int xPos_initial, int yPos_initial) {
        this.xPos = xPos_initial;
        this.yPos = yPos_initial;
        this.nPCSpriteSheet = npcSpriteSheet;
        this.nPCRectangle = new Rectangle(this.xPos - 45, this.yPos - 40, SpriteSheet.tileSize + 30, SpriteSheet.tileSize + 30);
        this.sprite = npcSpriteSheet.getSprite(0, 0);
        nPCRectangle.generateBorderGraphics(1, 0x194875);
        
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        renderHandler.loadSprite(this.sprite, this.xPos, this.yPos, scale);
        renderHandler.loadRectangle(this.nPCRectangle, scale);

        if (canInteract) {
            Graphics graphics = renderHandler.getViewGraphics();
            renderHandler.drawTextBubble("Press I For interaction", this.xPos, this.yPos - 20, 6 * scale, graphics);
            //this.canInteract = false;
        }
    }

    @Override
    public void update(GameFrame game) {

        /* TO DO :
         * rendre le npc statique
         * créer sa hitbox plus grande que lui afin de pouvoir interagir depuis plus loin
         * créer un overlay au dessus du personnage quand il peut interagir avec le npc
         * gérer la partie intéraction (dialogue etc)
         */
        
    }

    @Override
    public void transform(int dx, int dy, int dTheta) {
        this.xPos += dx;
        this.yPos += dy;
        this.nPCRectangle.moveX(dx);
        this.nPCRectangle.moveY(dy); 
    }

    public Rectangle getNPRectangle() {
        return this.nPCRectangle;
    }

    public void setcanInteract(boolean Bool) {
        this.canInteract = Bool;
    }

}
