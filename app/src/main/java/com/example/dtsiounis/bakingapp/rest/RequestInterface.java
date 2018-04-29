package com.example.dtsiounis.bakingapp.rest;

import com.example.dtsiounis.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
