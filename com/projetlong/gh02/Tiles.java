package com.projetlong.gh02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tiles {

    private SpriteSheet spriteSheet;

    public Tiles(File tilesFile, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;

        try (Scanner scanner = new Scanner(tilesFile)) {
            while(scanner.hasNextLine()) {
                String[] tilesString = scanner.nextLine().split("-");
                System.out.println(tilesString[0] + " is at position (" + tilesString[1] + ", " + tilesString[2] + ").");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file at location " + tilesFile.getAbsolutePath());
        }
    }

}
