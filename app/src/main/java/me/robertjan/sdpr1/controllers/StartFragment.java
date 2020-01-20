package me.robertjan.sdpr1.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.models.Photo;

public class StartFragment extends Fragment implements View.OnClickListener {

    private Photo photo;

    private static final int REQUEST_TAKE_PHOTO = 1;

    private static final int REQUEST_PICK_PHOTO = 2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        Button openCameraButton = view.findViewById(R.id.button_camera);
        openCameraButton.setOnClickListener(this);

        Button openGalleryButton = view.findViewById(R.id.button_gallery);
        openGalleryButton.setOnClickListener(this);

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    private void saveToStorage(Uri uri) throws IOException {
        File photo = null;
        Bitmap bitmap = null;

        try {
            photo = this.createImageFile();
            bitmap = MediaStore.Images.Media.getBitmap(
                    Objects.requireNonNull(getActivity()).getContentResolver(),
                    uri
            );
        } catch (IOException error) {
            error.printStackTrace();
        }

        if (photo != null && bitmap != null) {
            FileOutputStream stream = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {

            File photo = null;

            try {photo = this.createImageFile(); } catch (IOException error) {
                error.printStackTrace();
            }

            if (photo != null) {
                Uri uri = FileProvider.getUriForFile(
                        Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "me.robertjan.sdpr1.fileprovider",
                        photo
                );

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("input", ".jpg", storageDir);
        this.photo.background = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            this.photo.clearAllPlacables();
            ((MainActivity) Objects.requireNonNull(getActivity())).navigateTo(R.id.navigation_editor);
        }

        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            try { this.saveToStorage(data.getData()); } catch (IOException e) {
                e.printStackTrace();
            }

            this.photo.clearAllPlacables();
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
                this.openGallery();
                break;
        }
    }
}