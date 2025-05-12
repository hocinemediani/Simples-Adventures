package com.projetlong.gh02.inventory;

import java.util.*;

/**
 * Raresties for items.
 */
public enum Rarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;
}

/**
 * Base class for all inventory items.
 */
public abstract class Item {
    private final String id;
    private final String name;
    private final Rarity rarity;

    public Item(String id, String name, Rarity rarity) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        return String.format("%s (ID:%s) - %s", name, id, rarity);
    }
}
