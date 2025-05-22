package com.projetlong.gh02;

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
}