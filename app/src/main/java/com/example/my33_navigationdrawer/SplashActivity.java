package com.example.my33_navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent nextActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PreferenceManager.getString(SplashActivity.this, "nickname").length() == 0) {
                    // call Login Activity
                    nextActivity = new Intent(SplashActivity.this, MainActivity.class);

                } else {
                    // Call Next Activity
                    nextActivity = new Intent(SplashActivity.this, MainActivity2.class);
                    nextActivity.putExtra("STD_NUM", PreferenceManager.getString(SplashActivity.this, "nickname"));
                }

                startActivity(nextActivity);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
