package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.artempugachev.bakingapp.R;

public class MainActivity extends AppCompatActivity  {
    public static final String RECIPES_KEY = "recipe";
    public static final String STEP_EXTRA = "step";
    public static final String CURRENT_STEP_ID_EXTRA = "current_step_id";
    public static final String IS_TWO_PANE_EXTRA = "two_pane";
    public static final String RECIPE_IN_WIDGET_KEY = "recipe_widget_";
    public static final String RECIPE_ID_EXTRA = "recipe_id";
    public static final String RECIPE_NAME_EXTRA = "recipe_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
