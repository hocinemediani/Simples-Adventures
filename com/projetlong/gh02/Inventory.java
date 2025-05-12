package com.projetlong.gh02;

import java.util.*;

/**
 * Manages the player's inventory.
 */
public class Inventory {
    private final Map<String, Item> items = new LinkedHashMap<>();
    /** Pas besoin d'utiliser une linked hash map car on souhaite pouvoir acceder a tout l'inventaire dans l'ordre que l'on veut
     * Ajouter dans la hash map avec comme clef l'emplacmeent de l'inventaire occupé plutot
     * Changer implémentation de remove / add / get
     * get all items devrait retourner la hash map
     * aussi faire la partie graphique de l'inventaire
     */

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
}
