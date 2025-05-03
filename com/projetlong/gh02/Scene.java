package com.projetlong.gh02;

import java.io.File;

public class Scene {

    /**  */
    private final GameMap map;
    /**  */
    private final GameFrame game;

    /**  */
    public Scene(File mapFile, Tiles tiles, GameFrame game) {
        this.game = game;
        this.map = new GameMap(mapFile, tiles, game);
    }

}
