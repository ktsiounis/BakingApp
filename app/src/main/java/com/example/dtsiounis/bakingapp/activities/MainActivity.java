package com.example.dtsiounis.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dtsiounis.bakingapp.R;
import com.example.dtsiounis.bakingapp.model.Recipe;
import com.example.dtsiounis.bakingapp.rest.APIClient;
import com.example.dtsiounis.bakingapp.rest.RequestInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRecipes();

    }

    public void loadRecipes(){
        RequestInterface requestInterface = APIClient.getClient().create(RequestInterface.class);
        Call<ArrayList<Recipe>> call = requestInterface.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipes = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e("Response error", "onFailure: ", t);
            }
        });
    }


}
