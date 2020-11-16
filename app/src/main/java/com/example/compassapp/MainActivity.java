package com.example.compassapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity,new BrightnessFragment())
                .add(R.id.main_activity,new CompassFragment())
                .commit() ;


    }
}