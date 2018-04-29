package com.example.dtsiounis.bakingapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dtsiounis.bakingapp.R;
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

public class MainActivity extends AppCompatActivity implements RecipesRVAdapter.ItemClickListener {

    private ArrayList<Recipe> recipes;
    private RecipesRVAdapter recipesRVAdapter;

    @BindView(R.id.content_recipes) public RecyclerView recipesRV;
    @BindView(R.id.progressBar) public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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

    }
}
