package ru.artempugachev.bakingapp.ui.fragments;



import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.network.RecipeListLoader;
import ru.artempugachev.bakingapp.ui.adapters.RecipeAdapter;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;
import ru.artempugachev.bakingapp.ui.activity.RecipeDetailsActivity;
import ru.artempugachev.bakingapp.ui.widget.IngredientsWidgetProvider;

public class RecipeListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeListLoader.RecipeLoadListener,
        RecipeAdapter.RecipeClickListener {

    private static final int RECIPE_LIST_LOADER_ID = 42;

    @BindView(R.id.recipeRecycler)
    RecyclerView recipeRecyclerView;

    RecipeAdapter recipeAdapter;


    public RecipeListFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecipesRecycler();
        getLoaderManager().initLoader(RECIPE_LIST_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void setUpRecipesRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), calculateColumnsNumber(getActivity()));
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    /**
     * Calculates number of columns in grid based on device width
     * */
    private int calculateColumnsNumber (Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        // todo for dual pane divide width by the amount of weight allocated to the list
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 300;
        int columnsNumber = (int) (width / scalingFactor);
        return columnsNumber;
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
            storeInPref(recipes);
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
//            storeInPref(recipe);
//            updateWidget();

            // start recipe details activity
            Intent recipeDetailsActivityIntent = new Intent(getActivity(), RecipeDetailsActivity.class);
            recipeDetailsActivityIntent.putExtra(MainActivity.RECIPE_EXTRA, recipe);
            startActivity(recipeDetailsActivityIntent);
        }
    }

    /**
     * Updates ingredients in list widget
     * */
//    private void updateWidget() {
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
//        Intent updateWidgetIntent = new Intent();
//        updateWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//
//        updateWidgetIntent.setComponent(new ComponentName(getContext(), IngredientsWidgetProvider.class));
//
//        updateWidgetIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//
//        getContext().sendBroadcast(updateWidgetIntent);
//
//    }

    /**
     * Stores recipe in Shared Preferences for widget
     * */
    private void storeInPref(List<Recipe> recipes) {
        Gson gson = new Gson();

        String recipeJson = gson.toJson(recipes);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.RECIPE_EXTRA, recipeJson);
        editor.commit();
    }
}
