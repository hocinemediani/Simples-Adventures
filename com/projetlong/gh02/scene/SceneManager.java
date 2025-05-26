package com.projetlong.gh02.scene;

import com.projetlong.gh02.GameFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneManager {
    
    /** The current displayed scene. */
    private Scene currentScene;
    /** The list of all of the game's scenes. */
    private final ArrayList<Scene> sceneArray = new ArrayList<>();
    /** The instance of game being played. */
    private final GameFrame game;

    /** Main menu. */
    private final File mainMenuMapFile = new File("com/projetlong/gh02/mapFiles/mainmenu.txt");

    /** Creates an instance of SceneManager. A scene
     * manager keeps track of all of the scenes in
     * the game, and ease the scene loading process.
     * @param game The instance of game being played
     */
    public SceneManager(GameFrame game) {
        try (Scanner scanner = new Scanner(new File("com/projetlong/gh02/mapFiles/path.txt"))) {
            String[] informations = scanner.nextLine().split(";");
            /** Assets and files for the first scene. */
            File mapFile1 = new File(informations[3]);
            /** Assets and files for the second scene. */;
            File mapFile2 = new File(informations[4]);
            /** Assets and files for the third scene. */
            File mapFile3 = new File(informations[5]);
            /** Tiles file. */
            File tileFile = new File(informations[6]);
            
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mainMenuMapFile, tileFile, game, 0));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mainMenuMapFile, tileFile, game, 1));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mainMenuMapFile, tileFile, game, 2));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mainMenuMapFile, tileFile, game, 3));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mapFile1, tileFile, game, 4));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mapFile2, tileFile, game, 5));
            this.sceneArray.add(new Scene(informations[0], informations[1], informations[2], mapFile3, tileFile, game, 6));
            this.currentScene = sceneArray.getFirst();
        } catch (FileNotFoundException e) {
            System.out.println("No such file ");
        }
        this.game = game;
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
