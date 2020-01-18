package me.robertjan.sdpr1.controllers;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.models.Photo;
import me.robertjan.sdpr1.models.Placable;
import me.robertjan.sdpr1.models.Sticker;
import me.robertjan.sdpr1.models.Text;

public class EditorFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private Photo photo;

    private Placable selected;

    private RelativeLayout canvas;

    private LinearLayout controls;

    private Button saveButton;

    private SeekBar zoom;

    private int _xDelta, _yDelta;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        this.canvas = view.findViewById(R.id.canvas);
        this.canvas.setOnClickListener(this);

        this.controls = view.findViewById(R.id.controls);
        this.controls.setVisibility(View.INVISIBLE);

        this.zoom = view.findViewById(R.id.zoom);
        this.zoom.setOnSeekBarChangeListener(seekBarChangeListener);

        ImageButton nextButton = view.findViewById(R.id.next);
        nextButton.setOnClickListener(this);

        ImageButton deleteButton = view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(this);

        Button addTextButton = view.findViewById(R.id.add_text);
        addTextButton.setOnClickListener(this);

        Button addStickerButton = view.findViewById(R.id.add_sticker);
        addStickerButton.setOnClickListener(this);

        this.saveButton = view.findViewById(R.id.save);
        this.saveButton.setOnClickListener(this);

        ImageView background = view.findViewById(R.id.background);
        this.setBackground(background);

        for (Placable placable : this.photo.placables) {
            if (placable instanceof Sticker) {
                this.addStickerView((Sticker) placable);
            } else if (placable instanceof Text) {
                this.addTextView((Text) placable);
            }
        }

        return view;
    }

    private void setBackground(ImageView background) {
        if (this.photo.background != null) {
            File file = new File(this.photo.background);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        Objects.requireNonNull(getActivity()).getApplicationContext().getContentResolver(),
                        Uri.fromFile(file)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (bitmap != null) {
                background.setImageBitmap(bitmap);
            }
        }
    }

    private void newSticker() {
        Sticker sticker = new Sticker(View.generateViewId());

        this.photo.addPlacable(sticker);
        this.addStickerView(sticker);
    }

    private void newText() {
        Text text = new Text(View.generateViewId());

        this.photo.addPlacable(text);
        this.addTextView(text);
    }

    private void addStickerView(Sticker sticker) {
        ImageView view = new ImageView(this.getActivity());
        view.setId(sticker.id);
        view.setImageResource(sticker.getSticker());
        view.setOnTouchListener(this);
        view.setOnClickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sticker.getWidth(), sticker.getHeight());
        params.leftMargin = sticker.locationX;
        params.topMargin = sticker.locationY;

        view.setLayoutParams(params);
        this.canvas.addView(view);
    }

    private void addTextView(Text text) {
        TextView view = new TextView(this.getActivity());
        view.setId(text.id);
        view.setText(text.getValue());
        view.setOnTouchListener(this);
        view.setOnClickListener(this);

        view.setTextColor(getResources().getColor(text.getColor()));
        view.setTypeface(view.getTypeface(), Typeface.BOLD);
        view.setGravity(Gravity.CENTER);
        view.setTextSize(text.getSize());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = text.locationX;
        params.topMargin = text.locationY;

        view.setLayoutParams(params);
        this.canvas.addView(view);
    }

    private void nextSticker() {
        Sticker sticker = (Sticker) this.selected;
        sticker.nextSticker();

        ImageView view = this.canvas.findViewById(sticker.id);
        view.setImageResource(sticker.getSticker());
        this.setImageDimensions();
    }

    private void nextColor() {
        Text text = (Text) this.selected;
        text.nextColor();

        TextView view = this.canvas.findViewById(text.id);
        view.setTextColor(getResources().getColor(text.getColor()));
    }

    private void removePlacable() {
        this.photo.removePlacable(this.selected);
        this.canvas.removeView(this.canvas.findViewById(this.selected.id));
        this.placableDeselected();
    }

    private void placableSelected(View view) {
        this.selected = this.photo.getPlacableById(view.getId());
        this.zoom.setProgress(this.selected.zoom);
        this.controls.setVisibility(View.VISIBLE);
        this.saveButton.setVisibility(View.INVISIBLE);
    }

    private void placableDeselected() {
        this.selected = null;
        this.controls.setVisibility(View.INVISIBLE);
        this.saveButton.setVisibility(View.VISIBLE);
    }

    private void setImageDimensions() {
        ImageView view = this.canvas.findViewById(selected.id);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = selected.getWidth();
        params.height = selected.getHeight();

        view.setLayoutParams(params);
    }

    private void setTextSize() {
        TextView view = this.canvas.findViewById(selected.id);
        view.setTextSize(this.selected.getSize());
    }

    private void savePhoto() {
        this.canvas.setDrawingCacheEnabled(true);
        this.canvas.buildDrawingCache();

        Bitmap bitmap = this.canvas.getDrawingCache();

        try {
            File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile("output", ".jpg", storageDir);

            FileOutputStream stream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.canvas.setDrawingCacheEnabled(false);
    }

    public void onClick(View view) {
        // Button listeners
        switch (view.getId()) {
            case R.id.add_text:
                this.newText();
                return;
            case R.id.add_sticker:
                this.newSticker();
                return;
            case R.id.next:
                if (this.selected instanceof Sticker) {
                    this.nextSticker();
                } else if (this.selected instanceof Text) {
                    this.nextColor();
                }
                return;
            case R.id.delete:
                this.removePlacable();
                return;
            case R.id.save:
                this.savePhoto();
                ((MainActivity) Objects.requireNonNull(getActivity())).navigateTo(R.id.navigation_share);
                return;
        }

        // Placable listener
        if (((View) view.getParent()).getId() == R.id.canvas) {
            this.placableSelected(view);
        } else {
            this.placableDeselected();
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (this.selected != this.photo.getPlacableById(view.getId())) {
            return false;
        }

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // _xDelta and _yDelta record how far inside the view we have touched. These
                // values are used to compute new margins when the view is moved.
                _xDelta = X - view.getLeft();
                _yDelta = Y - view.getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.leftMargin = X - _xDelta;
                params.topMargin = Y - _yDelta;

                view.setLayoutParams(params);
                this.selected.setLocation(params.leftMargin, params.topMargin);

                break;
        }

        return true;
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            selected.setZoom(progress);

            if (selected instanceof Sticker) {
                setImageDimensions();
            } else if (selected instanceof Text) {
                setTextSize();
            }
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override public void onStopTrackingTouch(SeekBar seekBar) {}
    };
}