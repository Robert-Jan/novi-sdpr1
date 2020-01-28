package me.robertjan.sdpr1.models;

import java.util.ArrayList;

/**
 * Software Development Praktijk 1
 *
 * @author Robert-Jan van der Elst
 * @since 02-12-2019
 */
public class Photo {

    public String background;

    public ArrayList<Placeable> placeables;

    public Photo() {
       this.placeables = new ArrayList<>();
    }

    public void addPlacable(Placeable placeable) {
        this.placeables.add(placeable);
    }

    public void removePlacable(Placeable placeable) {
        this.placeables.remove(placeable);
    }

    public void clearAllPlacables() {
        this.placeables = new ArrayList<>();
    }

    public Placeable getPlacableById(Integer id) {
        for (Placeable placeable : this.placeables) {
            if (placeable.id.equals(id)) {
                return placeable;
            }
        }

        return null;
    }
}
