package me.robertjan.sdpr1.models;

public abstract class Placable {

    public Integer id;

    public Integer zoom = 100;

    public Integer width = 300;

    public Integer height = 300;

    public Integer locationX = 0;

    public Integer locationY = 0;

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public void setLocation(Integer x, Integer y) {
        this.locationX = x;
        this.locationY = y;
    }
}
