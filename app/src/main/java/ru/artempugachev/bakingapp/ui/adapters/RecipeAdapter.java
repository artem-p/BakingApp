package ru.artempugachev.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;

/**
 * Adapter for recipes recycler view
 */

public final class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    private List<Recipe> recipes;
    private RecipeClickListener recipeClickListener;

    public RecipeAdapter(Context context, RecipeClickListener recipeClickListener) {
        this.context = context;
        this.recipeClickListener = recipeClickListener;
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
     * Return recipe with specified position
     */
    public Recipe getRecipe(int position) {
        if (recipes != null && !recipes.isEmpty() && recipes.size() > position) {
            return recipes.get(position);
        } else {
            return null;
        }
    }


    /**
     * View holder
     */
    public final class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void fillRecipe(Recipe recipe) {
            String imageUrl = recipe.getImageUrl();
            if (!imageUrl.equals("")) {
                Picasso.with(context).load(imageUrl).into(recipeImage);
            }
            recipeTitle.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            recipeClickListener.onRecipeClick(getAdapterPosition());
        }
    }


    /**
     * Interface to handle clicks on recipe card
     */
    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }
}


