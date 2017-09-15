package ru.artempugachev.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.ui.recipedetails.StepsAdapter;

public class RecipeDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ingredientsTextView)
    TextView ingredientsTextView;

    @BindView(R.id.stepsRecycler)
    RecyclerView stepsRecycler;

    private StepsAdapter stepsAdapter;

    private Recipe recipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(MainActivity.RECIPE_EXTRA)) {
            recipe = intent.getParcelableExtra(MainActivity.RECIPE_EXTRA);
        }

        if (recipe != null) {
            fillViews(recipe);
        } else {
            showNoRecipe();
        }
    }

    /**
     * Fill recipe views with data
     * */
    private void fillViews(Recipe recipe) {
        setTitle(recipe.getName());

        String ingredientsText = recipe.toIngredientsText();
        ingredientsTextView.setText(ingredientsText);

        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        stepsRecycler.setLayoutManager(stepsLayoutManager);

        stepsAdapter = new StepsAdapter(recipe.getSteps());
        stepsRecycler.setAdapter(stepsAdapter);
    }

    /**
     * We have no recipe, show error text
     * */
    private void showNoRecipe() {

    }
}
