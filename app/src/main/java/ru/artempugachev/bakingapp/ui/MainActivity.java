package ru.artempugachev.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.artempugachev.bakingapp.R;

public class MainActivity extends AppCompatActivity {
    private static final String RECIPE_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
