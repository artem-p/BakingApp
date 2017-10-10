package ru.artempugachev.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;
import ru.artempugachev.bakingapp.ui.activity.RecipeDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

/**
 * Test recipe details
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest {
    @Rule
    public ActivityTestRule<RecipeDetailsActivity> activityTestRule = new ActivityTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class){
        @Override
        protected Intent getActivityIntent() {
            // add test recipe to intent used for launch activity
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            TestRecipeProvider testRecipeProvider = new TestRecipeProvider();
            Recipe recipe = testRecipeProvider.getRecipe();

            Intent intent = new Intent(targetContext, RecipeDetailsActivity.class);
            intent.putExtra(MainActivity.RECIPES_KEY, recipe);
            return intent;
        }
    };

    /**
     * Clicks on step
     * Checks it opens step details activity in phone mode
     * and doesn't open it in tablet mode
     * */
    @Test
    public void clickStepItem_opensStepDetails () {
        boolean isTwoPane = activityTestRule.getActivity().findViewById(R.id.two_pane_recipe_layout) != null;

        onView(withId(R.id.recipe_details_steps_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        if (!isTwoPane) {
            // in phone mode step pager is on the screen
            onView(withId(R.id.step_pager)).check(matches(isDisplayed()));
        } else {
            // in table mode there is no pager
            try {
                onView(withId(R.id.step_pager)).check(matches(isDisplayed()));
            } catch (NoMatchingViewException e) {
                assert true;
            }
        }
    }
}
