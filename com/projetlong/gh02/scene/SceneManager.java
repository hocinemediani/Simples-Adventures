package com.projetlong.gh02.scene;

import com.projetlong.gh02.GameFrame;
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
    private final File mapFile1 = new File("com/projetlong/gh02/mapFiles/testLevel1.txt");
    private final File tileFile = new File("com/projetlong/gh02/mapFiles/tiles.txt");
    /** Assets and files for the second scene. */
    private final File mapFile2 = new File("com/projetlong/gh02/mapFiles/testLevel2.txt");
    /** Assets and files for the third scene. */
    private final File mapFile3 = new File("com/projetlong/gh02/mapFiles/testLevel3.txt");

    /** Main menu. */
    private final File mainMenuMapFile = new File("com/projetlong/gh02/mapFiles/mainmenu.txt");

    /** Creates an instance of SceneManager. A scene
     * manager keeps track of all of the scenes in
     * the game, and ease the scene loading process.
     * @param game The instance of game being played
     */
    public SceneManager(GameFrame game) {
        this.game = game;
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mainMenuMapFile, tileFile, game, 0));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mainMenuMapFile, tileFile, game, 1));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mainMenuMapFile, tileFile, game, 2));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mainMenuMapFile, tileFile, game, 3));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile1, tileFile, game, 4));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile2, tileFile, game, 5));
        this.sceneArray.add(new Scene(backgroundPath, obstaclePath, decorationPath, mapFile3, tileFile, game, 6));
        this.currentScene = sceneArray.getFirst();
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
