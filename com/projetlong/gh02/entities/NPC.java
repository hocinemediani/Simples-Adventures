package com.projetlong.gh02.entities;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.handlers.RenderHandler;
import com.projetlong.gh02.sprite.Sprite;
import com.projetlong.gh02.sprite.SpriteSheet;
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

    private String text = "Press I to interact";
    private RenderHandler renderHandler;
    private int scale;

    public NPC(SpriteSheet npcSpriteSheet, int xPos_initial, int yPos_initial) {
        this.xPos = xPos_initial;
        this.yPos = yPos_initial;
        this.nPCSpriteSheet = npcSpriteSheet;
        this.nPCRectangle = new Rectangle(this.xPos - SpriteSheet.tileSize * 3, this.yPos - SpriteSheet.tileSize * 3, SpriteSheet.tileSize * 3, SpriteSheet.tileSize * 3);
        this.sprite = npcSpriteSheet.getSprite(0, 0);
        nPCRectangle.generateBorderGraphics(1, 0x194875);
        
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        this.renderHandler = renderHandler;
        this.scale = scale;
        renderHandler.loadSprite(this.sprite, this.xPos, this.yPos, scale);
        renderHandler.loadRectangle(this.nPCRectangle, scale);

        if (canInteract) {
            Graphics graphics = renderHandler.getViewGraphics();
            renderHandler.drawTextBubble(text, this.xPos, this.yPos - 20, 6 * scale, graphics);
            renderHandler.drawText("Albert", this.xPos, this.yPos + 200, 6 * scale, Color.WHITE, graphics);
        }
    }

    @Override
    public void update(GameFrame game) {

        if (canInteract && game.getInputHandler().isInteracting()) {
            startDialogue(scale, renderHandler.getViewGraphics());
        }
        /* TO DO :
         * rendre le npc statique
         * créer sa hitbox plus grande que lui afin de pouvoir interagir depuis plus loin
         * créer un overlay au dessus du personnage quand il peut interagir avec le npc
         * gérer la partie intéraction (dialogue etc)
         */
        
    }


    public void startDialogue(int scale, Graphics graphics) {
        this.canInteract = false;
        text = "Once upon a time, Cregut was a little guy.";
        this.canInteract = true;
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
