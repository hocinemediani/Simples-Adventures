package com.projetlong.gh02.inventory;

/**
 * Throwable grenades.
 */
public class Grenade extends Item {
    private final int damage;
    private final float radius;

    public Grenade(String id, String name, Rarity rarity, int damage, float radius) {
        super(id, name, rarity);
        this.damage = damage;
        this.radius = radius;
    }

    public int getDamage() {
        return damage;
    }

    public float getRadius() {
        return radius;
    }
}
