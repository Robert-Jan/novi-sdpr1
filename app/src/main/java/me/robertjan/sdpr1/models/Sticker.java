package me.robertjan.sdpr1.models;

import java.util.Random;

import me.robertjan.sdpr1.R;

public class Sticker extends Placable {

    private Drawable currentDrawable;

    private enum Drawable {
        SUNGLASS    (R.drawable.sunglass, 300, 150),
        CROWN       (R.drawable.crown, 300, 240),
        STAR        (R.drawable.star, 300, 290),
        HAIR        (R.drawable.hair, 300, 275),
        HAT         (R.drawable.hat, 300, 160);

        public final int id;
        public final int width;
        public final int height;

        Drawable(int id, int width, int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }
    }

    public Sticker(Integer id) {
        super(id);
        this.randomSticker();
    }

    public int getSticker() {
        return this.currentDrawable.id;
    }

    public void nextSticker() {
        int index = this.currentDrawable.ordinal();
        int nextIndex = index + 1;
        Drawable[] drawables = Drawable.values();
        nextIndex %= drawables.length;

        this.currentDrawable = drawables[nextIndex];
        this.setDimensions();
    }

    private void randomSticker() {
        Drawable[] drawables = Drawable.values();
        int index = (new Random()).nextInt(drawables.length);

        this.currentDrawable = drawables[index];
        this.setDimensions();
    }

    private void setDimensions() {
        this.width = this.currentDrawable.width;
        this.height = this.currentDrawable.height;
    }
}
