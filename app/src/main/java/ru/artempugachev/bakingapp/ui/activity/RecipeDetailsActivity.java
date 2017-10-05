package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.model.Step;
import ru.artempugachev.bakingapp.ui.adapters.StepsAdapter;
import ru.artempugachev.bakingapp.ui.fragments.RecipeDetailsFragment;
import ru.artempugachev.bakingapp.ui.fragments.StepFragment;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsAdapter.StepClickListener {
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
            addStepFragment(0);
        }
    }


    private void fillRecipeViews() {
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(MainActivity.RECIPES_KEY)) {
            recipe = intent.getParcelableExtra(MainActivity.RECIPES_KEY);
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelable(MainActivity.RECIPES_KEY, recipe);
        arguments.putBoolean(MainActivity.IS_TWO_PANE_EXTRA, isTwoPane);
        recipeDetailsFragment.setArguments(arguments);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.recipe_details_fragment_container, recipeDetailsFragment)
                .commit();

        if (recipe != null) {
            setTitle(recipe.getName());
        }

    }


    private void addStepFragment(int stepId) {
        Step step = recipe.getStep(stepId);
        StepFragment stepFragment = new StepFragment();
        Bundle arguments = new Bundle();

        arguments.putParcelable(MainActivity.STEP_EXTRA, step);
        arguments.putBoolean(MainActivity.IS_TWO_PANE_EXTRA, isTwoPane);
        stepFragment.setArguments(arguments);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.step_fragment_container, stepFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handle click on step. Show step details.
     * */
    @Override
    public void onStepClick(int stepPosition) {
        if (isTwoPane) {
            addStepFragment(stepPosition);
        } else {
            if (recipe != null) {
                Intent intent = new Intent(RecipeDetailsActivity.this, StepActivity.class);
                intent.putExtra(MainActivity.RECIPES_KEY, recipe);
                intent.putExtra(MainActivity.CURRENT_STEP_ID_EXTRA, stepPosition);
                startActivity(intent);
            }
        }

    }
}
