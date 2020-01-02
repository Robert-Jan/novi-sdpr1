package me.robertjan.sdpr1.models;

import me.robertjan.sdpr1.R;

public class Sticker extends Placable {

    public Integer drawableId;

    public Sticker(Integer id) {
        this.id = id;
    }

    public void setDrawable(Integer id) {
        this.drawableId = id;
    }

    public void getSticker() {

    }
}
