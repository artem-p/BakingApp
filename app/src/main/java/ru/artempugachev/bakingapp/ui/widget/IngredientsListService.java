package ru.artempugachev.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Ingredient;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;

/**
 * Remote views service to display ingredients list.
 */

public class IngredientsListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewFactory(this.getApplicationContext(), intent);
    }

}

class IngredientsListViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Intent intent;
    private Recipe recipe;

    public IngredientsListViewFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
        this.intent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipesJson = sharedPreferences.getString(MainActivity.RECIPES_KEY, "");

        // https://stackoverflow.com/questions/11350287/ongetviewfactory-only-called-once-for-multiple-widgets
        int recipeId = Integer.valueOf(intent.getData().getSchemeSpecificPart());

        if (!recipesJson.isEmpty() && recipeId != -1) {
            recipe = getRecipeFromJson(recipesJson, recipeId);
        } else {
            recipe = null;
        }
    }


    private Recipe getRecipeFromJson(String recipesJson, int recipeId) {
        Gson gson = new Gson();
        Type recipesListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(recipesJson, recipesListType);
        if (recipes != null && !recipes.isEmpty() && recipes.size() > recipeId) {
            return recipes.get(recipeId);
        } else {
            return null;
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipe != null && recipe.getIngredients() != null) {
            return recipe.getIngredients().size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = null;

        if (recipe != null) {
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients != null && !ingredients.isEmpty() && ingredients.size() > position) {
                String ingredient = ingredients.get(position).asText();
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_list_item);
                remoteViews.setTextViewText(R.id.ingredients_widget_list_item_text, ingredient);
            }
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
