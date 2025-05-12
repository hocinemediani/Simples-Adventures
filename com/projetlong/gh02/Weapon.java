package com.projetlong.gh02.inventory;

import java.util.*;

/**
 * Weapons that deal damage and can hold attachments.
 */
public class Weapon extends Item {
    private final int damage;
    private final int maxAmmo;
    private int currentAmmo;
    private final List<Attachment> attachments = new ArrayList<>();

    public Weapon(String id, String name, Rarity rarity, int damage, int maxAmmo) {
        super(id, name, rarity);
        this.damage = damage;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = maxAmmo;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void reload() {
        this.currentAmmo = maxAmmo;
    }

    public boolean fire() {
        if (currentAmmo > 0) {
            currentAmmo--;
            return true;
        }
        return false;
    }

    public void addAttachment(Attachment att) {
        attachments.add(att);
    }

    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Damage:%d Ammo:%d/%d Attachments:%s]", damage, currentAmmo, maxAmmo, attachments);
    }
}