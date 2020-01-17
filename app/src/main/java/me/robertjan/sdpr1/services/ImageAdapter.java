package me.robertjan.sdpr1.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private final Context context;

    private final ArrayList<String> images;

    public ImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Bitmap bitmap = BitmapFactory.decodeFile(images.get(position));

        ImageView image = new ImageView(context);
        image.setImageBitmap(bitmap);

        GridView.LayoutParams layoutParams = new GridView.LayoutParams(300, 300);
        image.setLayoutParams(layoutParams);

        TextView text = new TextView(context);
        text.setText(images.get(position));

        return image;
    }
}
