package com.example.producta;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public MyAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    public MyAdapter(@NonNull FragmentManager fm, int behavior, Context context, int totalTabs) {
        super(fm, behavior);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllProductsFragment allProductsFragment = new AllProductsFragment();
                return allProductsFragment;
            case 1:
                UploadProductsFragment uploadProductsFragment = new UploadProductsFragment();
                return uploadProductsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}