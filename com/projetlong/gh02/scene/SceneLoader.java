package com.projetlong.gh02.scene;

import com.projetlong.gh02.GameFrame;

public class SceneLoader {

    /** The scene manager of the game. */
    private final SceneManager sceneManager;
    /** The instance of game being played. */
    private final GameFrame game;

    /** Creates an instance of SceneLoader.
     * A scene loader transitions between scenes.
     * @param game The instance of game being played
     */
    public SceneLoader (GameFrame game) {
        this.sceneManager = game.getSceneManager();
        this.game = game;
    }


    /** Loads the scene with ID sceneID.
     * @param sceneID The ID of the scene to load
     */
    public void loadScene(int sceneID) {
        sceneManager.setCurrentScene(sceneID);
        game.updateCurrentScene();
    }

}
