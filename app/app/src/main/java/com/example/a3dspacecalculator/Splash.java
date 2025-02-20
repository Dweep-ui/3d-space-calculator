package com.example.a3dspacecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.logoImageView);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        imageView.startAnimation(animation);

        // Delay for 5 seconds (5000 milliseconds) before navigating to AdvanceUI
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, AdvanceUI.class);
                startActivity(intent);
                finish(); // Optional: Close the splash activity to prevent going back
            }
        }, 5000); // Change this value to the desired delay in milliseconds
    }
}


