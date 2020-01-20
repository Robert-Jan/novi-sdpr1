package me.robertjan.sdpr1.models;

import me.robertjan.sdpr1.R;

/**
 * Software Development Praktijk 1
 *
 * @author Robert-Jan van der Elst
 * @since 20-01-2020
 */
enum Drawable {
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