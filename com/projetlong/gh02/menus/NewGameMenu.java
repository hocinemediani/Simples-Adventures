package com.projetlong.gh02.menus;

import com.projetlong.gh02.GameFrame;
import com.projetlong.gh02.entities.GameObject;
import com.projetlong.gh02.handlers.RenderHandler;

public class NewGameMenu implements GameObject {

    private GameFrame game;

    public NewGameMenu(GameFrame game) {
        this.game = game;
    }

    @Override
    public void render(RenderHandler renderHandler, int scale) {
        
    }

    @Override
    public void update(GameFrame game) {
        
    }

    @Override
    public void transform(int dx, int dy, int dTheta) {}
    
}
