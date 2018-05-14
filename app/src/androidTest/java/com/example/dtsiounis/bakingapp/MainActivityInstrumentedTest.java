package com.example.dtsiounis.bakingapp;

import android.support.test.rule.*;
import android.support.test.runner.AndroidJUnit4;

import com.example.dtsiounis.bakingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void containsRecipe() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe(){
        // Perform a click on a recipe from the recyclers view
        onView(withId(R.id.content_recipes)).perform(actionOnItemAtPosition(0, click()));

        // Check if the recipes steps list activity is loaded
        onView(withText("Steps:")).check(matches(isDisplayed()));

        // Perform a click on a step from the recycler view
        onView(withId(R.id.recipe_list)).perform(actionOnItemAtPosition(1, click()));
    }
}
