package com.projetlong.gh02.inventory;

import java.util.*;

/**
 * Attachments that can be applied to weapons.
 */
public class Attachment {
    private final String name;
    private final String description;

    public Attachment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}