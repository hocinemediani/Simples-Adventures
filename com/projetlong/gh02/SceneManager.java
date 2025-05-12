package com.projetlong.gh02;

import java.io.File;
import java.util.ArrayList;

public class SceneManager {
    
    /** The current displayed scene. */
    private Scene currentScene;
    /** The list of all of the game's scenes. */
    private final ArrayList<Scene> sceneArray = new ArrayList<>();
    /** The instance of game being played. */
    private final GameFrame game;
    /** Path of the different layers. */
    private final String backgroundPath = "assets/backgroundTileSheet.png";
    private final String obstaclePath = "assets/obstacleTileSheet.png";
    private final String decorationPath = "assets/decorationTileSheet.png";
    /** Assets and files for the first scene. */
    private final File mapFile1 = new File("com/projetlong/gh02/testLevel1.txt");
    private final File tileFile1 = new File("com/projetlong/gh02/tiles.txt");
    /** Assets and files for the second scene. */
    private final File mapFile2 = new File("com/projetlong/gh02/testLevel2.txt");
    private final File tileFile2 = new File("com/projetlong/gh02/tiles.txt");
    /** Assets and files for the third scene. */
    private final File mapFile3 = new File("com/projetlong/gh02/testLevel3.txt");
    private final File tileFile3 = new File("com/projetlong/gh02/tiles.txt");

    /** Creates an instance of SceneManager. A scene
     * manager keeps track of all of the scenes in
     * the game, and ease the scene loading process.
     * @param game The instance of game being played
     */
    public SceneManager(GameFrame game) {
        this.game = game;
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile1, tileFile1, game, 1));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile2, tileFile2, game, 2));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile3, tileFile3, game, 3));
        this.currentScene = sceneArray.get(0);
    }


    /** Returns the current scene being displayed.
     * @return The current scene
     */
    public Scene getCurrentScene() {
        return this.currentScene;
    }


    /**  */
    public ArrayList<Scene> getScenes() {
        return this.sceneArray;
    }


    /** Sets the current scene to the scene
     * with ID sceneID.
     * @param sceneID The ID of the scene to load
     */
    public void setCurrentScene(int sceneID) {
        game.getRenderHandler().getCamera().moveX(-game.getRenderHandler().getCamera().getX());
        game.getRenderHandler().getCamera().moveY(-game.getRenderHandler().getCamera().getY());
        this.currentScene = sceneArray.get(sceneID % sceneArray.size());
    }

}
