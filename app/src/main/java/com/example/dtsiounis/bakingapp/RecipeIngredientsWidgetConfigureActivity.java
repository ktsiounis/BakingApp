package com.example.dtsiounis.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dtsiounis.bakingapp.activities.RecipeStepsListActivity;
import com.example.dtsiounis.bakingapp.adapters.RecipesRVAdapter;
import com.example.dtsiounis.bakingapp.model.Recipe;
import com.example.dtsiounis.bakingapp.rest.APIClient;
import com.example.dtsiounis.bakingapp.rest.RequestInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The configuration screen for the {@link RecipeIngredientsWidget RecipeIngredientsWidget} AppWidget.
 */
public class RecipeIngredientsWidgetConfigureActivity extends Activity implements RecipesRVAdapter.ItemClickListener {

    private static final String PREFS_NAME = "com.example.dtsiounis.bakingapp.RecipeIngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private RecipesRVAdapter recipesRVAdapter;
    private ArrayList<Recipe> recipes;

    @BindView(R.id.progressBar) public ProgressBar progressBar;
    @BindView(R.id.content_recipes) public RecyclerView recipesRV;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeIngredientsWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
//            String widgetText = mAppWidgetText.getText().toString();
//            saveTitlePref(context, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RecipeIngredientsWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public RecipeIngredientsWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        ButterKnife.bind(this);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.recipe_ingredients_widget_configure);
        //findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recipesRV.setLayoutManager(mLayoutManager);
        recipesRV.setItemAnimator(new DefaultItemAnimator());
        recipesRV.setHasFixedSize(true);
        recipesRVAdapter = new RecipesRVAdapter(this);
        recipesRV.setAdapter(recipesRVAdapter);

        if(isOnline()){
            loadRecipes();
        }
        else{
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }

    public void loadRecipes(){
        progressBar.setVisibility(View.VISIBLE);
        RequestInterface requestInterface = APIClient.getClient().create(RequestInterface.class);
        Call<ArrayList<Recipe>> call = requestInterface.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipes = response.body();
                Log.d("Ingredient", "onCreate: " + recipes.get(0).getIngredients().get(0).getIngredient());
                progressBar.setVisibility(View.GONE);
                recipesRVAdapter.swapList(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e("Response error", "onFailure: ", t);
            }
        });
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemClickListener(int position) {
        Log.d("Ingredient", "onCreate: " + recipes.get(position).getIngredients().get(position).getIngredient());
        Intent intent = new Intent(this, RecipeStepsListActivity.class);
        intent.putExtra("recipe", recipes.get(position));
        startActivity(intent);
    }
}

