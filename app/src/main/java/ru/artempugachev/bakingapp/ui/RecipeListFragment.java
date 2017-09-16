package ru.artempugachev.bakingapp.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        getLoaderManager().initLoader(RECIPE_LIST_LOADER_ID, null, this);
//        setUpRecipesRecycler();


        return rootView;
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onStartLoadingRecipes() {

    }

    @Override
    public void onFinishLoadingRecipes() {

    }

    @Override
    public void onRecipeClick(int position) {

    }
}
