package com.projetlong.gh02.inventory;

/**
 * Consumable items that restore health or shield.
 */
public class HealItem extends Item {
    public enum Type {HEALTH, SHIELD}

    private final Type type;
    private final int amount;

    public HealItem(String id, String name, Rarity rarity, Type type, int amount) {
        super(id, name, rarity);
        this.type = type;
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
