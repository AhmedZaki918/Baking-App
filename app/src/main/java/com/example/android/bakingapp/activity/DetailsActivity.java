package com.example.android.bakingapp.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.android.bakingapp.widget.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    // Main model class
    private Recipe mRecipe;

    // Steps data
    private List<Step> mStepList;
    private StepsAdapter mStepAdapter;
    @BindView(R.id.recycler_view_steps)
    RecyclerView mRecyclerStep;

    // Ingredients data
    private List<Ingredient> mIngredientList;
    private IngredientsAdapter mIngredientsAdapter;
    @BindView(R.id.recycler_view_ingredient)
    RecyclerView mRecyclerIngredient;

    // Layout manager
    private LinearLayoutManager mLayoutManager;

    // Recipe name TextView
    @BindView(R.id.tv_recipe_name)
    TextView recipeName;

    // String variable to store recipe name
    private String name;

    // Recipe image
    @BindView(R.id.image)
    ImageView recipeImage;

    // Variables to handle tablet landscape orientation
    @BindView(R.id.tv_ingredient_label)
    TextView ingredientsLabel;
    @Nullable
    @BindView(R.id.btn_show_hide)
    Button showHide;
    @BindView(R.id.tv_step_label)
    TextView stepLabel;
    private int index = 0;

    // Tablet case (Two or One pane)
    private boolean isTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        // Get data from Intent
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra(Constant.RECIPE);

        // Check the device is tablet or not
        if (findViewById(R.id.detailContainer) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

        // If it's tablet case, perform Two Pane Mode
        if (isTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            replace(detailFragment, R.id.detailContainer, getSupportFragmentManager().beginTransaction());

            // Change layout in landscape orientation
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                // Hide visibility, clear and SetText on views
                ingredientsLabel.setVisibility(View.GONE);
                mRecyclerIngredient.setVisibility(View.GONE);
                stepLabel.setText("");
                showHide.setVisibility(View.VISIBLE);
                showHide.setText(R.string.show_ingredients);

                // Set on click listener to hide and show the views
                showHide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (index == 0) {
                            showHide.setText(R.string.hide_ingredients);
                            ingredientsLabel.setVisibility(View.VISIBLE);
                            ingredientsLabel.setText("");
                            mRecyclerIngredient.setVisibility(View.VISIBLE);

                            index++;
                        } else {
                            showHide.setText(R.string.show_ingredients);
                            ingredientsLabel.setVisibility(View.GONE);
                            mRecyclerIngredient.setVisibility(View.GONE);
                            index = 0;
                        }
                    }
                });
            }
        }

        // Get the name of the recipe
        name = mRecipe.getName();
        recipeName.setText(name);

        // Set up adapter and recycler view for Ingredient data
        mIngredientList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerIngredient.setLayoutManager(mLayoutManager);

        // Set up adapter and recycler view for Steps data
        mStepList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerStep.setLayoutManager(mLayoutManager);

        // Calling the methods
        getIngredientsAndSteps();
        displayRecipeImage();

    }

    // Displays ingredients and Steps on the screen
    private void getIngredientsAndSteps() {
        Call<List<Recipe>> call = APIClient.getInstance().getApi().get_recipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                // For ingredients data
                mIngredientList = mRecipe.getIngredients();
                mIngredientsAdapter = new IngredientsAdapter(DetailsActivity.this, mIngredientList);
                mRecyclerIngredient.setAdapter(mIngredientsAdapter);

                // For Steps data
                mStepList = mRecipe.getSteps();
                mStepAdapter = new StepsAdapter(DetailsActivity.this, mStepList);
                mRecyclerStep.setAdapter(mStepAdapter);

                // Run the widget
                runWidget();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Displays recipe image on the screen by it's id
    private void displayRecipeImage() {
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

    // Helper method for fragment
    private void replace(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Run widget
    private void runWidget() {
        ArrayList<String> InPref = fillRow(mIngredientList);
        setPreferences("ingredients", InPref, DetailsActivity.this);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(DetailsActivity.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(DetailsActivity.this, BakingWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        BakingWidgetProvider.updateAppWidget(DetailsActivity.this, appWidgetManager, appWidgetIds);
    }

    // Setup preferences for widget
    private void setPreferences(String arrayName, ArrayList<String> array, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("appWidget", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.size());
        for (int i = 0; i < array.size(); i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        editor.apply();
    }

    // Draw the layout of the row
    private ArrayList<String> fillRow(List<Ingredient> ingredientsList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < ingredientsList.size(); i++) {
            String row = ingredientsList.get(i).getQuantity()
                    + " " + ingredientsList.get(i).getMeasure()
                    + " " + ingredientsList.get(i).getIngredient();
            arrayList.add(row);
        }
        return arrayList;
    }
}