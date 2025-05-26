package com.projetlong.gh02.menus;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.Rectangle;
import com.projetlong.gh02.entities.GameObject;
import com.projetlong.gh02.handlers.RenderHandler;
import java.awt.Color;

public class MainMenu implements GameObject {

    private final Rectangle settingsRectangle = new Rectangle();
    private final Rectangle newGameRectangle = new Rectangle();
    private final Rectangle loadGameRectangle = new Rectangle();
    private final GameFrame game;

    public MainMenu(GameFrame game) {
        this.game = game;
    }


    public void handleClick(int mouseX, int mouseY) {
        if (settingsRectangle.isClicked(mouseX, mouseY)) {
            System.out.println("Onglet Options");
            game.getSceneLoader().loadScene(1);
        }
        if (newGameRectangle.isClicked(mouseX, mouseY)) {
            System.out.println("Onglet Nouvelle Partie");
            game.getSceneLoader().loadScene(2);
        }
        if (loadGameRectangle.isClicked(mouseX, mouseY)) {
            System.out.println("Onglet Charger Partie");
            game.getSceneLoader().loadScene(3);
        }
    }


    @Override
    public void render(RenderHandler renderHandler, int scale) {
        settingsRectangle.generateGraphics(0xC0C0C0);
        settingsRectangle.generateBorderGraphics(20, 0xffffff);

        newGameRectangle.generateGraphics(0xC0C0C0);
        newGameRectangle.generateBorderGraphics(20, 0xffffff);

        loadGameRectangle.generateGraphics(0xC0C0C0);
        loadGameRectangle.generateBorderGraphics(20, 0xffffff);

        renderHandler.loadRectangle(settingsRectangle, 1);
        renderHandler.loadRectangle(newGameRectangle, 1);
        renderHandler.loadRectangle(loadGameRectangle, 1);
        
        renderHandler.drawText("Settings.", 200, 500, 100, Color.BLACK, game.getRenderHandler().getViewGraphics());
        System.out.println(settingsRectangle.getX() + settingsRectangle.getWidth() / 2);
        System.out.println(settingsRectangle.getY());
    }

    @Override
    public void update(GameFrame game) {
        /** Responsive settings rectangle. */
        settingsRectangle.setX((int) (0.015 * game.getWidth()));
        settingsRectangle.setY((int) (0.03 * game.getHeight()));
        settingsRectangle.setWidth((int) (0.3 * game.getWidth()));
        settingsRectangle.setHeight((int) (0.9 * game.getHeight()));

        /** Responsive new game rectangle. */
        newGameRectangle.setX((int) (0.345 * game.getWidth()));
        newGameRectangle.setY((int) (0.03 * game.getHeight()));
        newGameRectangle.setWidth((int) (0.3 * game.getWidth()));
        newGameRectangle.setHeight((int) (0.9 * game.getHeight()));

        /** Responsive load game rectangle. */
        loadGameRectangle.setX((int) (0.675 * game.getWidth()));
        loadGameRectangle.setY((int) (0.03 * game.getHeight()));
        loadGameRectangle.setWidth((int) (0.3 * game.getWidth()));
        loadGameRectangle.setHeight((int) (0.9 * game.getHeight()));
    }

    @Override
    public void transform(int dx, int dy, int dTheta) {}
    
}
