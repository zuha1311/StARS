package com.example.producta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread myThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), SlidersActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        myThread.start();


    }
}