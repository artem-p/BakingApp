package ru.artempugachev.bakingapp;

/**
 * Testing recipe list
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.artempugachev.bakingapp.ui.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test recipe list
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on recipe in list
     * Checks it opens up the RecipeDetails
     * */
    @Test
    public void clickRecipeItem_opensDetailsActivity() {
        onView(withId(R.id.recipeRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredientsTextView)).check(matches(isDisplayed()));
    }
}
