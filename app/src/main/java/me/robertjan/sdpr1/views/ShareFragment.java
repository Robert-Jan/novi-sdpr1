package me.robertjan.sdpr1.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import me.robertjan.sdpr1.R;
import me.robertjan.sdpr1.controllers.MainActivity;
import me.robertjan.sdpr1.models.Photo;

public class ShareFragment extends Fragment {

    private Photo photo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        this.photo = ((MainActivity) Objects.requireNonNull(getActivity())).getPhoto();

        return root;
    }
}