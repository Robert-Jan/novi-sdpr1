package me.robertjan.sdpr1.models;

/**
 * Software Development Praktijk 1
 *
 * @author Robert-Jan van der Elst
 * @since 02-12-2019
 */
public abstract class Placeable {

    public Integer id;

    public Integer zoom = 100;

    protected Integer width;

    protected Integer height;

    public Integer locationX = 0;

    public Integer locationY = 0;

    Placeable(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public float getSize() {
        return 24;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom >= 50 ? zoom : 50;
    }

    public void setLocation(Integer x, Integer y) {
        this.locationX = x;
        this.locationY = y;
    }
}
