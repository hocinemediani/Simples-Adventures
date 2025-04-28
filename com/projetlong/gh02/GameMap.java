package com.projetlong.gh02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMap {

    /** The different tiles used throughout the map. */
    private Tiles tiles;
    /** The ID of the main background tile. */
    private int fillTileID = -1;
    /** The array of mapped tiles for the map. */
    private ArrayList<MappedTile> mappedTileArray = new ArrayList<>();

    /** Creates an instance of Map from a map file
     * and a set of Tiles. A map represents the
     * different tiles that make up each levels.
     * @param mapFile The file that sets all tiles ID and positions
     * @param tiles The tile set used for the map
     */
    public GameMap(File mapFile, Tiles tiles) {
        this.tiles = tiles;
        try (Scanner scanner = new Scanner(mapFile)) {
            while(scanner.hasNextLine()) {
                String[] mapString = scanner.nextLine().split("-");
                if (mapString[0].contains("Fill")) {
                    fillTileID = Integer.parseInt(mapString[1]);
                    continue;
                }
                MappedTile mappedTile = new MappedTile(Integer.parseInt(mapString[0]),
                                                        Integer.parseInt(mapString[1]),
                                                        Integer.parseInt(mapString[2]));
                mappedTileArray.add(mappedTile);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file at location " + mapFile.getAbsolutePath());
        }
    }


    /**  */
    public void loadMap(RenderHandler renderHandler, int scale) {
        Rectangle camera = renderHandler.getCamera();
        for (int x = 0; x < camera.getWidth(); x += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
            for (int y = 0; y < camera.getHeight(); y += SpriteSheet.tileSize * GameFrame.GLOBALSCALE) {
                tiles.load(this.getfillTileID(), renderHandler, x, y, GameFrame.GLOBALSCALE);
            }
        }
        int increment = SpriteSheet.tileSize * scale;
        for(int tileIndex = 0; tileIndex < mappedTileArray.size(); tileIndex++) {
            MappedTile mappedTile = mappedTileArray.get(tileIndex);
            tiles.load(mappedTile.ID, renderHandler, mappedTile.xPos * increment, mappedTile.yPos * increment, scale);
        }
    }

    /** */
    public int getfillTileID() {
        return this.fillTileID;
    }

    class MappedTile {

        /** The tile ID. */
        public int ID;
        /** The x-coordinate to render the tile to. */
        public int xPos;
        /** The y-coordinate to render the tile to. */
        public int yPos;

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
