package me.robertjan.sdpr1.controllers;

import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    private int _xDelta, _yDelta;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        this.canvas = view.findViewById(R.id.canvas);
        this.canvas.setOnClickListener(this);

        this.controls = view.findViewById(R.id.controls);
        this.controls.setVisibility(View.INVISIBLE);

        SeekBar seekBar = view.findViewById(R.id.zoom);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        ImageButton deleteButton = view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(this);

        ImageButton nextButton = view.findViewById(R.id.next);
        nextButton.setOnClickListener(this);

        Button addTextButton = view.findViewById(R.id.add_text);
        addTextButton.setOnClickListener(this);

        Button addStickerButton = view.findViewById(R.id.add_sticker);
        addStickerButton.setOnClickListener(this);

        for (Placable placable : this.photo.placables) {
            if (placable instanceof Sticker) {
                this.addStickerView((Sticker) placable);
            } else if (placable instanceof Text) {
                Log.d("Editor", "Inflate text");
            }
        }

        return view;
    }

    private void newSticker() {
        Sticker sticker = new Sticker(View.generateViewId());

        this.photo.addPlacable(sticker);
        this.addStickerView(sticker);
    }

    private void addStickerView(Sticker sticker) {
        ImageView view = new ImageView(this.getActivity());
        view.setId(sticker.id);
        view.setImageResource(sticker.getSticker());
        view.setOnTouchListener(this);
        view.setOnClickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sticker.width, sticker.height);
        params.leftMargin = sticker.locationX;
        params.topMargin = sticker.locationY;

        view.setLayoutParams(params);
        this.canvas.addView(view);
    }

    private void nextSticker() {
        Sticker sticker = (Sticker) this.selected;
        sticker.nextSticker();

        ImageView view = this.canvas.findViewById(sticker.id);
        view.setImageResource(sticker.getSticker());
    }

    private void removePlacable() {
        this.photo.removePlacable(this.selected);
        this.canvas.removeView(this.canvas.findViewById(this.selected.id));
        this.placableDeselected();
    }

    private void placableSelected(View view) {
        this.selected = this.photo.getPlacableById(view.getId());
        this.controls.setVisibility(View.VISIBLE);
    }

    private void placableDeselected() {
        this.selected = null;
        this.controls.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        // Button listeners
        switch (view.getId()) {
            case R.id.add_text:
                Log.d("Editor", "Add text");
                return;
            case R.id.add_sticker:
                this.newSticker();
                return;
            case R.id.next:
                this.nextSticker();
                return;
            case R.id.delete:
                this.removePlacable();
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

        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override public void onStopTrackingTouch(SeekBar seekBar) {}
    };
}