package ru.artempugachev.bakingapp.ui;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.network.RecipeListLoader;

public class RecipeListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeListLoader.RecipeLoadListener,
        RecipeAdapter.RecipeClickListener {

    private static final int RECIPE_LIST_LOADER_ID = 42;
    public static final String RECIPE_EXTRA = "recipe";
    public static final String STEP_EXTRA = "step";

    @BindView(R.id.recipeRecycler)
    RecyclerView recipeRecyclerView;

    RecipeAdapter recipeAdapter;


    public RecipeListFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(RECIPE_LIST_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, rootView);


        setUpRecipesRecycler();


        return rootView;
    }

    private void setUpRecipesRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }


    /**
     * Loader methods
     * */
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RECIPE_LIST_LOADER_ID:
                return new RecipeListLoader(getActivity(), this);
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
            Intent recipeDetailsActivityIntent = new Intent(getActivity(), RecipeDetailsActivity.class);
            recipeDetailsActivityIntent.putExtra(RECIPE_EXTRA, recipe);
            startActivity(recipeDetailsActivityIntent);
        }
    }

}
