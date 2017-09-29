package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.artempugachev.bakingapp.R;

public class ChooseRecipeForWidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_recipe_for_widget);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(MainActivity.WIDGET_ID_KEY)) {
                int widgetId = intent.getIntExtra(MainActivity.WIDGET_ID_KEY, 0);
                String recipeInWidgetKey = MainActivity.RECIPE_IN_WIDGET_KEY + widgetId;
                if (intent.hasExtra(recipeInWidgetKey)) {
                    int recipeId = intent.getIntExtra(recipeInWidgetKey, 0);
                }
            }
        }
    }
}
