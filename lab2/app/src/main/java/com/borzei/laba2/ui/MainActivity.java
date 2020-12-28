package com.borzei.laba2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.borzei.laba2.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.host, MainFragment.newInstance(), null)
                    .commit();
        }
    }

}