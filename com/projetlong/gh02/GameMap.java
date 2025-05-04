package com.projetlong.gh02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class GameMap {

    /** The different tiles used throughout the map. */
    private Tiles tiles;
    /** The file containing the map info. */
    private File mapFile;
    /** The ID of the main background tile. */
    private int fillTileID = -1;
    /** The UI handler for construction mode. */
    private final MapEditor mapEditor;
    /** The instance of game being played. */
    private final GameFrame game;
    /** The file writer to write to the map file. */
    private PrintWriter fileWriter;
    /** The array of mapped tiles for the map. */
    private ArrayList<MappedTile> mappedTileArray = new ArrayList<>();
    /** The hashmap used to clean up the map file. */
    private HashMap<String, Integer> memoryHashMap = new HashMap<>();

    /** Creates an instance of Map from a map file
     * and a set of Tiles. A map represents the
     * different tiles that make up each levels.
     * @param mapFile The file that sets all tiles ID and positions
     * @param tiles The tile set used for the map
     * @param game The instance of game being played
     */
    public GameMap(File mapFile, Tiles tiles, GameFrame game) {
        this.mapFile = mapFile;
        this.tiles = tiles;
        this.game = game;
        this.mapEditor = new MapEditor(this.game);
        try {   
            fileWriter = new PrintWriter(new FileWriter(mapFile, true));
        } catch (IOException e) {
            System.out.println("Couldn't create the map file.");
        }
        scannerHelper(this::addMappedTile);
    }


    /** A helper function to go through a file and call
     * a method at each line.
     * @param methodToCall The method to call at each line
     */
    public final void scannerHelper(Consumer<int[]> methodToCall) {
        try (Scanner scanner = new Scanner(mapFile)) {
            while(scanner.hasNextLine()) {
                String[] args = scanner.nextLine().split(",");
                if (args[0].contains("Fill")) {
                    fillTileID = Integer.parseInt(args[1]);
                    continue;
                }
                int[] methodArgs = new int[3];
                methodArgs[0] = Integer.parseInt(args[0]);
                methodArgs[1] = Integer.parseInt(args[1]);
                methodArgs[2] = Integer.parseInt(args[2]);
                methodToCall.accept(methodArgs);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file at location " + mapFile.getAbsolutePath());
        }
    }


    /** Loads all the tiles from the map text file
     * into the graphics buffering strategy to then
     * be rendered.
     * @param scale The desired scaling ratio
     */
    public void loadMap(int scale) {
        if (this.getfillTileID() != -1) {
            fillBackground();
        }
        int increment = SpriteSheet.tileSize * scale;
        for(int tileIndex = 0; tileIndex < mappedTileArray.size(); tileIndex++) {
            MappedTile mappedTile = mappedTileArray.get(tileIndex);
            if (mappedTile != null) {
                tiles.load(mappedTile.ID, game.getRenderHandler(), mappedTile.xPos * increment, mappedTile.yPos * increment, scale);
            }
        }
    }


    /** Fills in the background with the tile of
     * tileID fillTileID.
     */
    public void fillBackground() {
        Rectangle camera = game.getRenderHandler().getCamera();
        for (int x = camera.getX() - SpriteSheet.tileSize - (camera.getX() % SpriteSheet.tileSize);
                                                    x < camera.getWidth() + camera.getX(); x += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
            for (int y = camera.getY() - SpriteSheet.tileSize - (camera.getY() % SpriteSheet.tileSize);
                                                    y < camera.getHeight() + camera.getY(); y += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
                tiles.load(this.getfillTileID(), game.getRenderHandler(), x, y, GameFrame.GLOBALSCALE);
            }
        }
    }


    /** Adds a mapped tile to the mappedTileArray.
     * This tile will then be rendered upon the next call
     * of the load map method.
     * @param tileInfo The tilesID, xPos and yPos of the tile
     */
    public final void addMappedTile(int[] tileInfo) {
        MappedTile newTile = new MappedTile(tileInfo[0], tileInfo[1], tileInfo[2]);
        mappedTileArray.add(newTile);
    }


    /** Deletes a mapped tile from the mappedTileArray.
     * The deleted tile will no longer be present in the
     * map file and will not be rendered anymore.
     * @param xPos The x-position of the tile to delete
     * @param yPos The y-position of the tile to delete
    */
    public void deleteMappedTile(int xPos, int yPos) {
        for (int i = 0; i < mappedTileArray.size(); i++) {
            if (mappedTileArray.get(i).xPos == xPos && mappedTileArray.get(i).yPos == yPos) {
                mappedTileArray.remove(i);
            }
        }
    }


    /** Writes to the map file the information
     * needed to save the desired tile to the map.
     * @param tile The tile to append to the file
     */
    private void writeToFile(MappedTile tile) {
        String tileString = tile.xPos + "," + tile.yPos;
        writeToFile(tileString, tile.ID);
    }


    /** Writes to the map file the information
     * needed to save the desired tile to the map.
     * @param mapString The xPos and yPos of the tile
     * @param tileID The ID of the tile
     */
    public void writeToFile(String mapString, int tileID) {
        fileWriter.write(tileID + "," + mapString + "\n");
        fileWriter.flush();
    }


    /** Clears the file and adds the Fill line
     * if the fillTIleID has been initialized.
     */
    public void clearFile() {
        try {
            fileWriter = new PrintWriter(new FileWriter(mapFile, false));
            if (fillTileID != -1) {
                fileWriter.append("Fill," + fillTileID + "\n");
            }
        } catch (IOException e) {
            System.out.println("Couldn't create the map file.");
        }
        
    }


    /** Cleans up the map file to remove duplicates and
     * account for newly created / deleted tiles.
     */
    public void cleanupMapFile() {
        clearFile();
        mappedTileArray.forEach(this::writeToFile);
        scannerHelper(this::fillingHashMap);
        clearFile();
        memoryHashMap.forEach(this::writeToFile);
    }


    /** Helper function called for each object
     * in the memory hash map to add an entry of 
     * each mapped tile to it.
     * @param tileInfo The tile informations (tileID, xPos, yPos)
     */
    public void fillingHashMap(int[] tileInfo) {
        String hashKey = tileInfo[1] + "," + tileInfo[2];
        memoryHashMap.put(hashKey, tileInfo[0]);
    }
    

    /** Returns the fillTileID which
     * corresponds to the ID of the tile
     * constituing the background of the map.
     * @return The fillTileID
     */
    public int getfillTileID() {
        return this.fillTileID;
    }


    /** Returns the MapEditor.
     * @return The map editor
     */
    public MapEditor getMapEditor() {
        return this.mapEditor;
    }


    /** Returns the tiles ued for the map.
     * @return The tiles of the map
     */
    public Tiles getTiles() {
        return this.tiles;
    }


    class MappedTile {

        /** The tile ID. */
        private int ID;
        /** The x-coordinate to render the tile to. */
        private int xPos;
        /** The y-coordinate to render the tile to. */
        private int yPos;

        /** Creates an instance of MappedTile from the
         * tile ID and the x and y coordinates to render
         * the tile to.
         * @param ID The tile ID
         * @param xPos The x-coordinate to render the tile to.
         * @param yPos The y-coordinate to render the tile to.
         */
        public MappedTile(int ID, int xPos, int yPos) {
            this.ID = ID;
            this.xPos = xPos;
            this.yPos = yPos;
        }
    }
}
