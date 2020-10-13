package com.example.blood4lux.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.blood4lux.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        long delayMillis;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, RegisterActivity.class));
                finish();
            }
        },delayMillis=2500);
    }
}
