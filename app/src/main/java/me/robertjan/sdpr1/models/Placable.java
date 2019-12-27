package me.robertjan.sdpr1.models;

public abstract class Placable {

    public Integer id;

    public Integer zoom;

    public Integer locationX;

    public Integer locationY;

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public void setLocation(Integer x, Integer y) {

    }
}
