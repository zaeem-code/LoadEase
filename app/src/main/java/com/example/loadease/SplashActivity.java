package com.example.loadease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.loadeasex.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3 * 1000);

                    Intent i = new Intent(getBaseContext(), SignIn_signUp.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}