package com.example.advertise;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MoviesActivity extends AppCompatActivity {
    private static String JSON_URL = "https://run.mocky.io/v3/f0919f56-3d93-4e36-8898-55795216d8e9";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }
}