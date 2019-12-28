package me.robertjan.sdpr1.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import me.robertjan.sdpr1.R;

public class EditorFragment extends Fragment implements View.OnClickListener {

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_editor, container, false);

        Button addTextButton = this.view.findViewById(R.id.add_text);
        addTextButton.setOnClickListener(this);

        Button addStickerButton = this.view.findViewById(R.id.add_sticker);
        addStickerButton.setOnClickListener(this);

        return this.view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_text:
                Log.d("Editor", "Add text");
                break;
            case R.id.add_sticker:
                this.addSticker();
                break;
        }
    }

    private void addSticker() {
        RelativeLayout parent = this.view.findViewById(R.id.image);

        ImageView sticker = new ImageView(this.getActivity());
        sticker.setImageResource(R.drawable.crown);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        sticker.setLayoutParams(params);

        parent.addView(sticker);
    }
}