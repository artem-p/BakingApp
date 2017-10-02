package ru.artempugachev.bakingapp.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.artempugachev.bakingapp.R;

public class WidgetConfigurationActivity extends AppCompatActivity {
    private int widgetId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_recipe_for_widget);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
//                String recipeInWidgetKey = MainActivity.RECIPE_IN_WIDGET_KEY + widgetId;
//                if (intent.hasExtra(recipeInWidgetKey)) {
//                    int recipeId = intent.getIntExtra(recipeInWidgetKey, 0);
//                }
            }
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }
}
