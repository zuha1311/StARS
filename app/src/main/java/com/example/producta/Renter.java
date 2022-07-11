package com.example.producta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class Renter extends AppCompatActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter);

        backButton = findViewById(R.id.historyBackBtn);
        initViewPager();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Renter.super.onBackPressed();
            }
        });
    }

    private void initViewPager() {
        final ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("All Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Upload"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}