package com.projetlong.gh02;

import java.io.File;
import java.util.ArrayList;

public class SceneManager {
    
    /**  */
    private Scene currentScene;
    /**  */
    private ArrayList<Scene> sceneArray = new ArrayList<>();
    /**  */
    private final GameFrame game;
    /**  */
    private int currentSceneID = 1;
    /**  */
    private final String backgroundPath1 = "assets/backgroundTileSheet.png";
    private final File mapFile1 = new File("com/projetlong/gh02/testLevel1.txt");
    private final File tileFile1 = new File("com/projetlong/gh02/tiles.txt");
    /**  */
    private final String backgroundPath2 = "assets/backgroundTileSheet.png";
    private final File mapFile2 = new File("com/projetlong/gh02/testLevel2.txt");
    private final File tileFile2 = new File("com/projetlong/gh02/tiles.txt");
    /**  */
    private final String backgroundPath3 = "assets/backgroundTileSheet.png";
    private final File mapFile3 = new File("com/projetlong/gh02/testLevel3.txt");
    private final File tileFile3 = new File("com/projetlong/gh02/tiles.txt");

    /**  */
    public SceneManager(GameFrame game) {
        this.game = game;
        this.sceneArray.add(new Scene(backgroundPath1, mapFile1, tileFile1, game, 1));
        this.sceneArray.add(new Scene(backgroundPath2, mapFile2, tileFile2, game, 2));
        this.sceneArray.add(new Scene(backgroundPath3, mapFile3, tileFile3, game, 3));
        this.currentScene = sceneArray.get(0);
    }


    /**  */
    public Scene getCurrentScene() {
        return this.currentScene;
    }


    /**  */
    public void setCurrentScene(int sceneID) {
        game.getRenderHandler().getCamera().moveX(-game.getRenderHandler().getCamera().getX());
        game.getRenderHandler().getCamera().moveY(-game.getRenderHandler().getCamera().getY());
        this.currentScene = sceneArray.get(sceneID % sceneArray.size());
    }

}
