package me.robertjan.sdpr1.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import me.robertjan.sdpr1.R;

public class EditorFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private View selected;

    private RelativeLayout canvas;

    private LinearLayout controls;

    private int _xDelta, _yDelta;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);

        this.canvas = view.findViewById(R.id.canvas);
        this.canvas.setOnClickListener(this);

        this.controls = view.findViewById(R.id.controls);
        this.controls.setVisibility(View.INVISIBLE);

        SeekBar seekBar = view.findViewById(R.id.zoom);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        Button addTextButton = view.findViewById(R.id.add_text);
        addTextButton.setOnClickListener(this);

        Button addStickerButton = view.findViewById(R.id.add_sticker);
        addStickerButton.setOnClickListener(this);

        return view;
    }

    private void addSticker() {
        ImageView sticker = new ImageView(this.getActivity());
        sticker.setImageResource(R.drawable.crown);
        sticker.setOnTouchListener(this);
        sticker.setOnClickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        sticker.setLayoutParams(params);

        this.canvas.addView(sticker);
    }

    private void placableSelected(View view) {
        this.selected = view;
        this.controls.setVisibility(View.VISIBLE);
    }

    private void placableDeselected(View view) {
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
                this.addSticker();
                return;
        }

        // Placable listener
        if (((View) view.getParent()).getId() == R.id.canvas) {
            this.placableSelected(view);
        } else {
            this.placableDeselected(view);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (this.selected != view) {
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

                params.removeRule(RelativeLayout.CENTER_IN_PARENT);

                params.leftMargin = X - _xDelta;
                params.topMargin = Y - _yDelta;

                view.setLayoutParams(params);
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