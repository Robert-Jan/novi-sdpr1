package me.robertjan.sdpr1.models;

import java.util.Random;

import me.robertjan.sdpr1.R;

public class Sticker extends Placable {

    private Drawable currentDrawable;

    private enum Drawable {
        SUNGLASS    (R.drawable.sunglass),
        CROWN       (R.drawable.crown),
        STAR        (R.drawable.star),
        HAIR        (R.drawable.hair),
        HAT         (R.drawable.hat);

        private final int id;

        private static Drawable[] values = values();

        Drawable(int id) {
            this.id = id;
        }

        public int getResource() {
            return this.id;
        }
    }

    public Sticker(Integer id) {
        this.id = id;
        this.randomSticker();
    }

    public int getSticker() {
        return this.currentDrawable.getResource();
    }

    public void nextSticker() {
        int index = this.currentDrawable.ordinal();
        int nextIndex = index + 1;
        Drawable[] drawables = Drawable.values();
        nextIndex %= drawables.length;

        this.currentDrawable = drawables[nextIndex];
    }

    private void randomSticker() {
        Drawable[] drawables = Drawable.values();
        int index = (new Random()).nextInt(drawables.length);
        this.currentDrawable = drawables[index];
    }
}
