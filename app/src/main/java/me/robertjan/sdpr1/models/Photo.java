package me.robertjan.sdpr1.models;

import java.util.ArrayList;

public class Photo {

    public String background;

    public ArrayList<Placable> placables;

    public Photo() {
       this.placables = new ArrayList<>();
    }

    public void addPlacable(Placable placable) {
        this.placables.add(placable);
    }

    public void removePlacable(Placable placable) {
        this.placables.remove(placable);
    }

    public Placable getPlacableById(Integer id) {
        for (Placable placable : this.placables) {
            if (placable.id.equals(id)) {
                return placable;
            }
        }

        return null;
    }

    public void saveToGallery() {

    }
}
