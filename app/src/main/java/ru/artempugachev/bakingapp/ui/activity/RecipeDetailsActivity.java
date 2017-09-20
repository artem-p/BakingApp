package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.model.Step;
import ru.artempugachev.bakingapp.ui.fragments.RecipeDetailsFragment;
import ru.artempugachev.bakingapp.ui.fragments.StepFragment;

public class RecipeDetailsActivity extends AppCompatActivity  {
    private Recipe recipe = null;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // check two pane mode
        isTwoPane = findViewById(R.id.two_pane_recipe_layout) != null;

        fillRecipeViews();

        if (isTwoPane) {
            fillStepViews();
        }
    }


    private void fillRecipeViews() {
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(MainActivity.RECIPE_EXTRA)) {
            recipe = intent.getParcelableExtra(MainActivity.RECIPE_EXTRA);
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelable(MainActivity.RECIPE_EXTRA, recipe);
        recipeDetailsFragment.setArguments(arguments);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.recipe_details_fragment_container, recipeDetailsFragment)
                .commit();

        if (recipe != null) {
            setTitle(recipe.getName());
        }

    }


    private void fillStepViews() {
        Step step = recipe.getStep(0);
        StepFragment stepFragment = new StepFragment();
        Bundle arguments = new Bundle();

        arguments.putParcelable(MainActivity.STEP_EXTRA, step);
        stepFragment.setArguments(arguments);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.step_fragment_container, stepFragment)
                .commit();
    }
}
