package com.example.dtsiounis.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.example.dtsiounis.bakingapp.R;
import com.example.dtsiounis.bakingapp.fragments.RecipeStepsDetailFragment;
import com.example.dtsiounis.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeStepsListActivity}.
 */
public class RecipeStepsDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Step step = getIntent().getParcelableExtra(RecipeStepsDetailFragment.ARG_ITEM_ID);
            Log.d("DetailsActivity", "onCreate: " + step.getDescription());

            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepsDetailFragment.ARG_ITEM_ID, step);
            RecipeStepsDetailFragment fragment = new RecipeStepsDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

}
