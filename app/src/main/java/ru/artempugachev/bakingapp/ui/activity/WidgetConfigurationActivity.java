package ru.artempugachev.bakingapp.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;

public class WidgetConfigurationActivity extends AppCompatActivity {
    private int widgetId;
    private List<Recipe> recipes;

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
        String recipesJson = preferences.getString(MainActivity.RECIPES_KEY, "");
        if (recipesJson.isEmpty()) {
            Gson gson = new Gson();
            Type recipesListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
            recipes = gson.fromJson(recipesJson, recipesListType);
        }
    }
}
