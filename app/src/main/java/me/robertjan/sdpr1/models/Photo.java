package me.robertjan.sdpr1.models;

import java.util.ArrayList;

public class Photo {

    public Integer id;

    public String path;

    public ArrayList<Placable> placables;

    public void addPlacable(Placable placable) {
        this.placables.add(placable);
    }

    public void saveToGallery() {

    }
}
