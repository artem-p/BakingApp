package ru.artempugachev.bakingapp.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.ui.adapters.StepsAdapter;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;

/**
 * List of ingredients and steps to prepare
 */

public class RecipeDetailsFragment extends Fragment {
    @BindView(R.id.ingredientsTextView)
    TextView ingredientsTextView;

    @BindView(R.id.recipe_details_steps_recycler)
    RecyclerView stepsRecycler;

    private StepsAdapter stepsAdapter;
    private LinearLayoutManager stepsLayoutManager;
    private Parcelable stepsRecyclerState;

    private Recipe recipe;
    private boolean isTwoPane;

    public RecipeDetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_details_fragment, container, false);
        ButterKnife.bind(this, rootView);


        Bundle arguments = getArguments();
        if (arguments != null) {
            recipe = arguments.getParcelable(MainActivity.RECIPES_KEY);

            isTwoPane = arguments.getBoolean(MainActivity.IS_TWO_PANE_EXTRA);

            if (recipe != null) {
                fillViews(recipe);
            } else {
                showNoRecipe();
            }

        }

        return rootView;
    }

    /**
     * Fill recipe views with data
     * */
    public void fillViews(Recipe recipe) {
        String ingredientsText = recipe.toIngredientsText();
        ingredientsTextView.setText(ingredientsText);

        stepsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stepsRecycler.setLayoutManager(stepsLayoutManager);

        stepsAdapter = new StepsAdapter(recipe.getSteps(), (StepsAdapter.StepClickListener) getActivity(), isTwoPane);
        stepsRecycler.setAdapter(stepsAdapter);
    }


    /**
     * We have no recipe, show error text
     * */
    public void showNoRecipe() {

    }
}
