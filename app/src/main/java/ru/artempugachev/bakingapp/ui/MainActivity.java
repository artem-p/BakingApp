package ru.artempugachev.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.network.RecipeListLoader;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeListLoader.RecipeLoadListener,
        RecipeAdapter.RecipeClickListener {

    private static final int RECIPE_LIST_LOADER_ID = 42;
    public static final String RECIPE_EXTRA = "recipe";
    public static final String STEP_EXTRA = "step";

    @BindView(R.id.recipeRecycler)
    RecyclerView recipeRecyclerView;

    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(RECIPE_LIST_LOADER_ID, null, this);

        setUpRecipesRecycler();
    }


    private void setUpRecipesRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }


    /**
     * Loader Callbacks
     * */
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RECIPE_LIST_LOADER_ID:
                return new RecipeListLoader(this, this);
            default:
                throw new RuntimeException("Loader not implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        if (recipes != null && !recipes.isEmpty()) {
            recipeAdapter.setRecipes(recipes);
        } else {
            // todo handle no recipes
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }


    /**
     * Recipe load listener methods. Use it to show/hide progressbar while loading recipe list
     * */
    @Override
    public void onStartLoadingRecipes() {

    }


    @Override
    public void onFinishLoadingRecipes() {

    }


    /**
     * Handle click on recipe card. Show recipe details.
     * */
    @Override
    public void onRecipeClick(int position) {
        Recipe recipe = recipeAdapter.getRecipe(position);

        if (recipe != null) {
            Intent recipeDetailsActivityIntent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
            recipeDetailsActivityIntent.putExtra(RECIPE_EXTRA, recipe);
            startActivity(recipeDetailsActivityIntent);
        }
    }
}
