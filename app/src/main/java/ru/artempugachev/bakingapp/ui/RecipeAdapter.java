package ru.artempugachev.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;

/**
 * Adapter for recipes recycler view
 */

public final class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;

    public RecipeAdapter() {
    }


    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recipeCardLayoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(recipeCardLayoutId, parent, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        if (this.recipes != null && !this.recipes.isEmpty()) {
            holder.fillRecipe(this.recipes.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }


    /**
     * View holder
     * */
    public final class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeTitle)
        TextView recipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillRecipe(Recipe recipe) {
            recipeTitle.setText(recipe.getName());
        }
    }
}


