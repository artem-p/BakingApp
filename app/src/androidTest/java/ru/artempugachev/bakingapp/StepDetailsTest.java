package ru.artempugachev.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;
import ru.artempugachev.bakingapp.ui.activity.RecipeDetailsActivity;
import ru.artempugachev.bakingapp.ui.activity.StepActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Testing step details
 */
@RunWith(AndroidJUnit4.class)
public class StepDetailsTest {
    @Rule
    public ActivityTestRule<StepActivity> activityTestRule = new ActivityTestRule<StepActivity>(StepActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            TestRecipeProvider testRecipeProvider = new TestRecipeProvider();
            Recipe recipe = testRecipeProvider.getRecipe(0);

            Intent intent = new Intent(targetContext, RecipeDetailsActivity.class);
            intent.putExtra(MainActivity.RECIPES_KEY, recipe);
            return intent;
        }
    };

    /**
     * Check correct step description
     * */
    @Test
    public void correctStepDescription() {
        // check step description
        onView(allOf(
                withText("Recipe Introduction"),
                withId(R.id.step_description)))
                .check(matches(isDisplayed()));

        // swipe to the step 3
        onView(withId(R.id.step_pager)).perform(swipeLeft());
        onView(withId(R.id.step_pager)).perform(swipeLeft());
        onView(withId(R.id.step_pager)).perform(swipeLeft());

        // check step description
        onView(allOf(
                withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature."),
                withId(R.id.step_description)))
                .check(matches(isDisplayed()));
    }
}
