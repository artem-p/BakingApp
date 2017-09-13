package ru.artempugachev.bakingapp.network;

import android.content.Context;
import android.support.v4.content.Loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.artempugachev.bakingapp.model.Recipe;

/**
 * Loads recipe list from provided network source
 */

public class RecipeListLoader extends Loader<List<Recipe>> {
    private static final String RECIPE_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private List<Recipe> recipes;
    private RecipeLoadListener recipeLoadListener;

    public RecipeListLoader(Context context, RecipeLoadListener recipeLoadListener) {
        super(context);
        this.recipeLoadListener = recipeLoadListener;
    }

    @Override
    protected void onStartLoading() {
        if (recipes != null) {
            deliverResult(recipes);
        } else {
            forceLoad();
        }
    }


    @Override
    public void deliverResult(List<Recipe> data) {
        recipes = data;
        super.deliverResult(data);
    }


    @Override
    protected void onForceLoad() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request recipeListRequest = new Request.Builder()
                .url(RECIPE_JSON_URL)
                .build();

        recipeLoadListener.onStartLoadingRecipes();
        okHttpClient.newCall(recipeListRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                recipeLoadListener.onFinishLoadingRecipes();
                deliverResult(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                recipeLoadListener.onFinishLoadingRecipes();
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();

                    Gson gson = new Gson();
                    Type recipeListType = new TypeToken<List<Recipe>>(){}.getType();
                    List<Recipe> recipes = gson.fromJson(responseStr, recipeListType);

                    deliverResult(recipes);
                } else {
                    deliverResult(null);
                }
            }
        });

    }

    public interface RecipeLoadListener {
        void onStartLoadingRecipes();
        void onFinishLoadingRecipes();
    }
}
