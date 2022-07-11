package com.example.producta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UploadProductsFragment extends Fragment {

    private View uploadProductsView;

    public UploadProductsFragment()
    {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        uploadProductsView = inflater.inflate(R.layout.fragment_uploadnew,container,false);

        return uploadProductsView;
    }
}
