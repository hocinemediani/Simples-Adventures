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
    /**  */
    private File mapFile;
    /** The ID of the main background tile. */
    private int fillTileID = -1;
    /**  */
    private final MapEditor mapEditor;
    /**  */
    private final GameFrame game;
    /**  */
    private PrintWriter fileWriter;
    /** The array of mapped tiles for the map. */
    private ArrayList<MappedTile> mappedTileArray = new ArrayList<>();
    /**  */
    private HashMap<String, Integer> memoryHashMap = new HashMap<>();

    /** Creates an instance of Map from a map file
     * and a set of Tiles. A map represents the
     * different tiles that make up each levels.
     * @param mapFile The file that sets all tiles ID and positions
     * @param tiles The tile set used for the map
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
     * @param renderHandler The game's render handler
     * @param scale The desired scaling ratio
     */
    public void loadMap(RenderHandler renderHandler, int scale) {
        Rectangle camera = renderHandler.getCamera();
        if (this.getfillTileID() != -1) {
            for (int x = camera.getX() - SpriteSheet.tileSize - (camera.getX() % SpriteSheet.tileSize);
                                                    x < camera.getWidth() + camera.getX(); x += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
                for (int y = camera.getY() - SpriteSheet.tileSize - (camera.getY() % SpriteSheet.tileSize);
                                                    y < camera.getHeight() + camera.getY(); y += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
                    tiles.load(this.getfillTileID(), renderHandler, x, y, GameFrame.GLOBALSCALE);
                }
            }
        }
        
        int increment = SpriteSheet.tileSize * scale;
        for(int tileIndex = 0; tileIndex < mappedTileArray.size(); tileIndex++) {
            MappedTile mappedTile = mappedTileArray.get(tileIndex);
            if (mappedTile == null) {
                return;
            }
            tiles.load(mappedTile.ID, renderHandler, mappedTile.xPos * increment, mappedTile.yPos * increment, scale);
        }
    }


    /**  */
    public final void addMappedTile(int[] tileInfo) {
        MappedTile newTile = new MappedTile(tileInfo[0], tileInfo[1], tileInfo[2]);
        mappedTileArray.add(newTile);
    }


    /**  */
    public void deleteMappedTile(int xPos, int yPos) {
        for (int i = 0; i < mappedTileArray.size(); i++) {
            if (mappedTileArray.get(i).xPos == xPos && mappedTileArray.get(i).yPos == yPos) {
                mappedTileArray.remove(i);
            }
        }
    }


    /**  */
    private void writeToFile(MappedTile tile) {
        String tileString = tile.xPos + "," + tile.yPos;
        writeToFile(tileString, tile.ID);
    }


    /**  */
    public void writeToFile(String mapString, int tileID) {
        fileWriter.write(tileID + "," + mapString + "\n");
        fileWriter.flush();
    }


    /**  */
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


    /**  */
    public void cleanupMapFile() {
        clearFile();
        mappedTileArray.forEach(this::writeToFile);
        scannerHelper(this::fillingHashMap);
        clearFile();
        memoryHashMap.forEach(this::writeToFile);
    }


    /**  */
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


    /**  */
    public MapEditor getMapEditor() {
        return this.mapEditor;
    }


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
