package ru.artempugachev.bakingapp.ui.adapters;

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
 * Adapter for recipe list in widget
 */

public class WidgetRecipeAdapter extends RecyclerView.Adapter<WidgetRecipeAdapter.WidgetRecipeViewHolder> {
    private List<Recipe> recipes;
    private RecipeAdapter.RecipeClickListener recipeClickListener;

    public WidgetRecipeAdapter(RecipeAdapter.RecipeClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public WidgetRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recipeItemLayoutId = R.layout.widget_configuration_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View itemView = inflater.inflate(recipeItemLayoutId, parent, shouldAttachToParentImmediately);
        return new WidgetRecipeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(WidgetRecipeViewHolder holder, int position) {
        if (this.recipes != null && !this.recipes.isEmpty()) {
            holder.fillRecipe(this.recipes.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public String getRecipeName(int position) {
        if (recipes != null && !recipes.isEmpty() && recipes.size() > position) {
            return recipes.get(position).getName();
        } else
            return "";
    }

    public class WidgetRecipeViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
        @BindView(R.id.widget_configuration_recipe_item)
        TextView recipeTitle;

        public WidgetRecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void fillRecipe(Recipe recipe) {
            recipeTitle.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            recipeClickListener.onRecipeClick(getAdapterPosition());
        }
    }
}
