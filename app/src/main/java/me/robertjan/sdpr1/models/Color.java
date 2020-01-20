package me.robertjan.sdpr1.models;

import me.robertjan.sdpr1.R;

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