package ru.artempugachev.bakingapp.ui;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.network.RecipeListLoader;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeListLoader.RecipeLoadListener {

    private static final int RECIPE_LIST_LOADER_ID = 42;

    @BindView(R.id.recipeRecycler)
    RecyclerView recipeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(RECIPE_LIST_LOADER_ID, null, this);
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

        } else {
            // todo handle no recipes
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }


    /**
     * Recipe load listener methods
     * */
    @Override
    public void onStartLoadingRecipes() {

    }

    @Override
    public void onFinishLoadingRecipes() {

    }
}
