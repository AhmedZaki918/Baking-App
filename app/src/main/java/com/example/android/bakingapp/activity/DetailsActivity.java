package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.Constant;
import com.example.android.bakingapp.adapter.IngredientsAdapter;
import com.example.android.bakingapp.adapter.StepsAdapter;
import com.example.android.bakingapp.fragment.DetailFragment;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.network.APIClient;
import com.example.android.bakingapp.widget.DisplayIngredientsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {


    private Recipe mRecipe;
    private LinearLayoutManager mLayoutManager;


    private List<Step> mStepList;
    private StepsAdapter mStepAdapter;
    private RecyclerView mRecyclerStep;


    private List<Ingredient> mIngredientList;
    private IngredientsAdapter mIngredientsAdapter;
    private RecyclerView mRecyclerIngredient;


    private TextView recipeName;
    private String name;
    private ImageView recipeImage;

    private TextView ingredientsLabel;

    private Button showHide;
    private TextView stepLabel;
    int i = 0;

    private boolean isTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recipeName = findViewById(R.id.tv_recipe_name);
        recipeImage = findViewById(R.id.image);
        ingredientsLabel = findViewById(R.id.tv_ingredient_label);
        mRecyclerIngredient = findViewById(R.id.recycler_view_ingredient);
        showHide = findViewById(R.id.btn_show_hide);
        stepLabel = findViewById(R.id.tv_step_label);


        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra(Constant.RECIPE);


        if (findViewById(R.id.detailContainer) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }


        if (isTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            replace(detailFragment, R.id.detailContainer, getSupportFragmentManager().beginTransaction());

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ingredientsLabel.setVisibility(View.GONE);
                mRecyclerIngredient.setVisibility(View.GONE);

                stepLabel.setText("");
                showHide.setVisibility(View.VISIBLE);
                showHide.setText("Show Ingredients");

                showHide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (i == 0) {
                            showHide.setText("Hide Ingredients");
                            ingredientsLabel.setVisibility(View.VISIBLE);
                            ingredientsLabel.setText("");
                            mRecyclerIngredient.setVisibility(View.VISIBLE);

                            i++;
                        } else if (i == 1) {
                            showHide.setText("Show Ingredients");
                            ingredientsLabel.setVisibility(View.GONE);
                            mRecyclerIngredient.setVisibility(View.GONE);
                            i = 0;
                        }
                    }
                });
            }
        }


        name = mRecipe.getName();
        recipeName.setText(name);


        mIngredientList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerIngredient.setLayoutManager(mLayoutManager);


        mRecyclerStep = findViewById(R.id.recycler_view_steps);
        mStepList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerStep.setLayoutManager(mLayoutManager);


        getIngredientsAndSteps();
        displayRecipeImage();
    }

    public void getIngredientsAndSteps() {
        Call<List<Recipe>> call = APIClient.getInstance().getApi().get_recipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {


                mIngredientList = mRecipe.getIngredients();
                mIngredientsAdapter = new IngredientsAdapter(DetailsActivity.this, mIngredientList);
                mRecyclerIngredient.setAdapter(mIngredientsAdapter);

                mStepList = mRecipe.getSteps();
                mStepAdapter = new StepsAdapter(DetailsActivity.this, mStepList);
                mRecyclerStep.setAdapter(mStepAdapter);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayRecipeImage() {
        switch (mRecipe.getId()) {
            case 1:
                recipeImage.setImageResource(R.drawable.nutella_pie);
                break;
            case 2:
                recipeImage.setImageResource(R.drawable.brownies);
                break;
            case 3:
                recipeImage.setImageResource(R.drawable.yelleow_cake);
                break;
            case 4:
                recipeImage.setImageResource(R.drawable.cheese_cake);
                break;
        }
    }

    private void replace(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}