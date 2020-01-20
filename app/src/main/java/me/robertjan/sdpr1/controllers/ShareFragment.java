package me.robertjan.sdpr1.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.support.ImageAdapter;

public class ShareFragment extends Fragment {

    private ArrayList<String> images;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        this.images = this.getImages();

        GridView grid = view.findViewById(R.id.grid);
        ImageAdapter adapter = new ImageAdapter(getActivity(), images);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                shareImage(images.get(position));
            }
        });

        return view;
    }

    private ArrayList<String> getImages() {
        File file = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        ArrayList<String> list = new ArrayList<>();

        if (file != null && file.isDirectory()) {
            File[] files = file.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("output");
                }
            });

            if (files != null) {
                for (File value : files) {
                    list.add(value.getAbsolutePath());
                }
            }

        }

        return list;
    }

    private void shareImage(String uri) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(uri)));

        startActivity(Intent.createChooser(intent, "Share your image with:"));
    }
}