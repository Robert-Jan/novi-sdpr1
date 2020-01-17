package me.robertjan.sdpr1.controllers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.models.Photo;

public class StartFragment extends Fragment implements View.OnClickListener {

    private Photo photo;

    private static final int REQUEST_TAKE_PHOTO = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        Button openCameraButton = view.findViewById(R.id.button_camera);
        openCameraButton.setOnClickListener(this);

        Button openGalleryButton = view.findViewById(R.id.button_gallery);
        openGalleryButton.setOnClickListener(this);

        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {

            File photo = null;

            try {photo = createImageFile(); } catch (IOException error) {
                error.printStackTrace();
            }

            if (photo != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "me.robertjan.sdpr1.fileprovider",
                        photo
                );

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("photo", ".jpg", storageDir);
        this.photo.background = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ((MainActivity) Objects.requireNonNull(getActivity())).navigateTo(R.id.navigation_editor);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_camera:
                this.openCamera();
                break;
            case R.id.button_gallery:
                Log.d("Start", "Open gallery");
                break;
        }
    }
}