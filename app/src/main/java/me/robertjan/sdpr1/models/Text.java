package me.robertjan.sdpr1.models;

import java.util.Calendar;

import me.robertjan.sdpr1.R;

public class Text extends Placable {

    private String value;

    private Color currentColor;

    private enum Color {
        ORANGE  (R.color.colorPrimary),
        GREEN   (R.color.green),
        BLUE    (R.color.blue),
        RED     (R.color.red);

        public final int id;

        Color(int id) {
            this.id = id;
        }
    }

    private String[] months = {
            "januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september",
            "oktober", "november", "december"
    };

    public Text(Integer id) {
        super(id);
        this.currentColor = Color.ORANGE;
        this.setValue("NOVI medewerker van \n" + this.getMonth());
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public float getSize() {
        return (24 / 100.0f) * this.zoom;
    }

    public int getColor() {
        return this.currentColor.id;
    }

    public void nextColor() {
        int index = this.currentColor.ordinal();
        int nextIndex = index + 1;
        Color[] colors = Color.values();
        nextIndex %= colors.length;

        this.currentColor = colors[nextIndex];
    }

    private String getMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        return this.months[month];
    }
}
