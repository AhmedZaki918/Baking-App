package com.example.android.bakingapp.data.network;

import com.example.android.bakingapp.data.model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("baking.json")
    Single<List<Recipe>> get_recipe();
}