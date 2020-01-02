package me.robertjan.sdpr1.models;

import java.util.ArrayList;

public class Photo {

    public Integer id;

    public String path;

    public ArrayList<Placable> placables;

    public Photo() {
       this.placables = new ArrayList<>();
    }

    public void addPlacable(Placable placable) {
        this.placables.add(placable);
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
