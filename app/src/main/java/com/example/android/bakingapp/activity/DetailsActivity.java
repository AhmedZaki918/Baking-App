package com.example.android.bakingapp.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    public Recipe mRecipe;

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
    @SuppressWarnings("FieldCanBeLocal")
    private LinearLayoutManager mLayoutManager;

    // Recipe name
    @BindView(R.id.tv_recipe_name)
    TextView recipeName;

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

    @BindView(R.id.tv_num_ingred)
    TextView ingredientsNum;
    @BindView(R.id.tv_num_steps)
    TextView stepsNum;
    @BindView(R.id.iv_back)
    ImageView backBtn;

    // Tablet case (Two or One pane)
    @SuppressWarnings("FieldCanBeLocal")
    private boolean isTwoPane;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        // Hide action bar
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        // Get the data via Intent
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra(Constant.RECIPE);

        /** --Tablet Case--
         *  Check the device is Tablet or not
         */
        isTwoPane = findViewById(R.id.detailContainer) != null;
        // If it's tablet case, perform Two Pane Mode
        if (isTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            replace(detailFragment, getSupportFragmentManager().beginTransaction());

            // Change the layout in landscape orientation
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                // Hide visibility, clear and Set Text on views
                ingredientsLabel.setVisibility(View.GONE);
                mRecyclerIngredient.setVisibility(View.GONE);
                stepLabel.setText("");
                assert showHide != null;
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

        // Display recipe name on view
        recipeName.setText(mRecipe.getName());

        // Setup Adapter and RecyclerView for ingredients data
        mIngredientList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerIngredient.setLayoutManager(mLayoutManager);

        // Setup Adapter and RecyclerView for steps data
        mStepList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerStep.setLayoutManager(mLayoutManager);

        // Calling the methods
        getIngredientsAndSteps();
        displayRecipeImage();

        // Set Click Listener on Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Displays ingredients and steps on the screen
     */
    private void getIngredientsAndSteps() {
        Call<List<Recipe>> call = APIClient.getInstance().getApi().get_recipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {

                // For ingredients data
                mIngredientList = mRecipe.getIngredients();
                mIngredientsAdapter = new IngredientsAdapter(DetailsActivity.this, mIngredientList);
                mRecyclerIngredient.setAdapter(mIngredientsAdapter);
                ingredientsNum.setText(String.valueOf(mIngredientsAdapter.getItemCount()));

                // For Steps data
                mStepList = mRecipe.getSteps();
                mStepAdapter = new StepsAdapter(DetailsActivity.this, mStepList);
                mRecyclerStep.setAdapter(mStepAdapter);
                stepsNum.setText(String.valueOf(mStepAdapter.getItemCount()));

                // Run the widget
                runWidget();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Toast.makeText(DetailsActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Displays recipe image on the screen by it's id
     */
    public void displayRecipeImage() {
        switch (mRecipe.getId()) {
            case 1:
                recipeImage.setImageResource(R.drawable.nut);
                break;
            case 2:
                recipeImage.setImageResource(R.drawable.brown);
                break;
            case 3:
                recipeImage.setImageResource(R.drawable.yellow);
                break;
            default:
                recipeImage.setImageResource(R.drawable.chess);
        }
    }

    /**
     * Helper method for fragment
     *
     * @param fragment            is the fragment itself
     * @param fragmentTransaction is the transaction of the fragment
     */
    private void replace(Fragment fragment, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.detailContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Setup of the widget
     */
    private void runWidget() {
        ArrayList<String> InPref = fillRow(mIngredientList);
        setPreferences(InPref, DetailsActivity.this);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(DetailsActivity.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(DetailsActivity.this, BakingWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        BakingWidgetProvider.updateAppWidget(DetailsActivity.this, appWidgetManager, appWidgetIds);
    }

    /**
     * Setup preferences for widget
     *  @param array     array list of String
     * @param mContext  the current context
     */
    private void setPreferences(ArrayList<String> array, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("appWidget", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("ingredients" + "_size", array.size());
        for (int i = 0; i < array.size(); i++)
            editor.putString("ingredients" + "_" + i, array.get(i));
        editor.apply();
    }

    /**
     * Draw the layout of the row
     *
     * @param ingredientsList array list of Ingredient
     * @return array list for each row
     */
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