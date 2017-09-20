package ru.artempugachev.bakingapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import ru.artempugachev.bakingapp.ui.activity.StepActivity;

/**
 * List of ingredients and steps to prepare
 */

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepClickListener {
    @BindView(R.id.ingredientsTextView)
    TextView ingredientsTextView;

    @BindView(R.id.stepsRecycler)
    RecyclerView stepsRecycler;

    private StepsAdapter stepsAdapter;

    private Recipe recipe;

    public RecipeDetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_details_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();
        if (arguments != null) {
            recipe = arguments.getParcelable(MainActivity.RECIPE_EXTRA);

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

        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stepsRecycler.setLayoutManager(stepsLayoutManager);

        stepsAdapter = new StepsAdapter(recipe.getSteps(), this);
        stepsRecycler.setAdapter(stepsAdapter);
    }

    /**
     * We have no recipe, show error text
     * */
    public void showNoRecipe() {

    }


    /**
     * Handle click on step. Show step details.
     * */
    @Override
    public void onStepClick(int stepPosition) {
        if (recipe != null) {
            Intent intent = new Intent(getActivity(), StepActivity.class);
            intent.putExtra(MainActivity.RECIPE_EXTRA, recipe);
            intent.putExtra(MainActivity.CURRENT_STEP_ID_EXTRA, stepPosition);
            startActivity(intent);
        }
    }
}
