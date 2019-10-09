package com.example.android.bakingapp.network;

import com.example.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("baking.json")
    Call<List<Recipe>> get_recipe();


}
