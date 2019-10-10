package com.example.android.bakingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.MainAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    /**
     * Initialize the variables
     */
    private MainAdapter mMainAdapter;
    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipeList;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);

        // Calling the method
        getRecipeName();
    }

    // Display recipes names on the main screen
    private void getRecipeName() {

        Call<List<Recipe>> call = APIClient.getInstance().getApi().get_recipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                mRecipeList = response.body();
                mMainAdapter = new MainAdapter(MainActivity.this, mRecipeList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setAdapter(mMainAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}