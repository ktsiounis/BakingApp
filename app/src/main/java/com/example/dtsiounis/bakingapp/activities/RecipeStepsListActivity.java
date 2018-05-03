package com.example.dtsiounis.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.dtsiounis.bakingapp.R;

import com.example.dtsiounis.bakingapp.adapters.SimpleItemRecyclerViewAdapter;
import com.example.dtsiounis.bakingapp.model.Ingredient;
import com.example.dtsiounis.bakingapp.model.Recipe;
import com.example.dtsiounis.bakingapp.model.Step;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepsDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepsListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.ingredientsTV)
    public TextView ingredientsTV;


    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);

        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        setSupportActionBar(toolbar);
        toolbar.setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(recipe.getName());

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        for(int i=0; i<ingredients.size(); i++){
            ingredientsTV.setText(ingredientsTV.getText() + " •"
                    + ingredients.get(i).getIngredient()
                    + " (" + ingredients.get(i).getQuantity()
                    + ingredients.get(i).getMeasure() + ")");
        }

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, steps, mTwoPane));
    }

}
