package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.artempugachev.bakingapp.R;

public class MainActivity extends AppCompatActivity  {
    public static final String RECIPE_EXTRA = "recipe";
    public static final String STEP_EXTRA = "step";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
