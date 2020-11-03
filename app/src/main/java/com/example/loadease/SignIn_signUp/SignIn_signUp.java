package com.example.loadease.SignIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loadease.ProfileActivity;
import com.example.loadease.R;
import com.example.loadease.SettingActivity;

public class SignIn_signUp extends AppCompatActivity implements View.OnClickListener {

    LinearLayout sign_in;
    TextView sign_up;
    ImageView facebook;
    ImageView twitter;
    ImageView google;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        sign_in = findViewById(R.id.sign_in);
        sign_in.setOnClickListener(this);
        sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(this);
        facebook = findViewById(R.id.facebook_img);
        facebook.setOnClickListener(this);
        twitter = findViewById(R.id.twitter_img);
        twitter.setOnClickListener(this);
        google = findViewById(R.id.google_img);
        google.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.sign_in){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        }else if (id == R.id.sign_up){
            Intent signUp = new Intent(this, SettingActivity.class);
            startActivity(signUp);
        }
    }
}