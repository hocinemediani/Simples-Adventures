package com.projetlong.gh02.inventory;

import java.util.*;

/**
 * Manages the player's inventory.
 */
public class Inventory {
    private final Map<String, Item> items = new LinkedHashMap<>();

    public void addItem(Item item) {
        items.put(item.getId(), item);
    }

    public void removeItem(String id) {
        items.remove(id);
    }

    public Item getItem(String id) {
        return items.get(id);
    }

    public Collection<Item> getAllItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public List<Weapon> getWeapons() {
        List<Weapon> list = new ArrayList<>();
        for (Item i : items.values()) if (i instanceof Weapon) list.add((Weapon)i);
        return list;
    }

    public List<HealItem> getHealItems() {
        List<HealItem> list = new ArrayList<>();
        for (Item i : items.values()) if (i instanceof HealItem) list.add((HealItem)i);
        return list;
    }

    public List<Grenade> getGrenades() {
        List<Grenade> list = new ArrayList<>();
        for (Item i : items.values()) if (i instanceof Grenade) list.add((Grenade)i);
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory:\n");
        for (Item i : items.values()) sb.append(" - ").append(i).append("\n");
        return sb.toString();
    }
}
