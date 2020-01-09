package me.robertjan.sdpr1.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.models.Photo;

public class  StartFragment extends Fragment implements View.OnClickListener {

    private Photo photo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_start, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        Button openCameraButton = root.findViewById(R.id.button_camera);
        openCameraButton.setOnClickListener(this);

        Button openGalleryButton = root.findViewById(R.id.button_gallery);
        openGalleryButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_camera:
                Log.d("Start", "Open camera");
                break;
            case R.id.button_gallery:
                Log.d("Start", "Open gallery");
                break;
        }
    }
}