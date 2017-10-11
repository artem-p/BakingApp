package ru.artempugachev.bakingapp;

/**
 * Testing recipe list
 */

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.artempugachev.bakingapp.ui.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Test recipe list
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Click on recipe in list
     * Check it opens up the RecipeDetails
     * */
    @Test
    public void clickRecipeItem_opensDetailsActivity() {
        onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredientsTextView)).check(matches(isDisplayed()));
    }

    /**
     * Click on recipe in list
     * Check correct recipe name in details activity header
     * */
    @Test
    public void clickRecipeItem_detailsWithCorrectHeader() {
        onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(
                isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Nutella Pie")));

        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(
                isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Brownies")));
    }
}
