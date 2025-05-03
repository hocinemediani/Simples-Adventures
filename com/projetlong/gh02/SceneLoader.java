package com.projetlong.gh02;

public class SceneLoader {

    /**  */
    private final SceneManager sceneManager;
    /**  */
    private final GameFrame game;

    /**  */
    public SceneLoader (SceneManager sceneManager, GameFrame game) {
        this.sceneManager = sceneManager;
        this.game = game;
    }

    public void loadScene(int sceneID) {
        sceneManager.setCurrentScene(sceneID);
        game.updateCurrentScene();
    }

}
