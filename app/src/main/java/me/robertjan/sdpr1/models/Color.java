package me.robertjan.sdpr1.models;

import me.robertjan.sdpr1.R;

/**
 * Software Development Praktijk 1
 *
 * @author Robert-Jan van der Elst
 * @since 20-01-2020
 */
enum Color {
    ORANGE  (R.color.colorPrimary),
    GREEN   (R.color.green),
    BLUE    (R.color.blue),
    RED     (R.color.red);

    public final int id;

    Color(int id) {
        this.id = id;
    }
}