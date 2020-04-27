package com.example.my33_navigationdrawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.utils.PreferenceManager;

public class FirstAuthActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_auth);

        if(PreferenceManager.getString(FirstAuthActivity.this, "nickname").length() == 0) {
            // call Login Activity
            intent = new Intent(FirstAuthActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            intent = new Intent(FirstAuthActivity.this, MainActivity2.class);
            intent.putExtra("STD_NUM", PreferenceManager.getString(FirstAuthActivity.this, "nickname"));
            startActivity(intent);
            this.finish();
        }
    }
}