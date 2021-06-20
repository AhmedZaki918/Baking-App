package com.example.android.bakingapp.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.data.model.Step;
import com.example.android.bakingapp.databinding.ActivityDetailsBinding;
import com.example.android.bakingapp.ui.adapter.Constant;
import com.example.android.bakingapp.ui.adapter.IngredientsAdapter;
import com.example.android.bakingapp.ui.adapter.StepsAdapter;
import com.example.android.bakingapp.ui.video.VideoActivity;
import com.example.android.bakingapp.util.OnStepClick;
import com.example.android.bakingapp.util.ViewUtils;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,
        OnStepClick {


    private Recipe recipes;
    private ActivityDetailsBinding binding;
    private StepsAdapter stepsAdapter;
    private IngredientsAdapter ingredientsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        initViews();
        updateUi();
        binding.ivBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
    }


    @Override
    public void onItemClick(Step currentItem) {
        Intent intent = new Intent(DetailsActivity.this, VideoActivity.class);
        intent.putExtra(Constant.STEPS, currentItem);
        this.startActivity(intent);
    }


    private void initViews() {
        recipes = getIntent().getParcelableExtra(Constant.RECIPE);
        if (recipes != null) {
            stepsAdapter = new StepsAdapter(recipes.getSteps(), this);
            ingredientsAdapter = new IngredientsAdapter(recipes.getIngredients());
        }
    }


    private void updateUi() {
        // Steps adapter
        ViewUtils.setupRecyclerView(binding.recyclerViewSteps,
                new LinearLayoutManager(this),
                stepsAdapter);
        // Ingredients adapter
        ViewUtils.setupRecyclerView(binding.recyclerViewIngredient,
                new LinearLayoutManager(this),
                ingredientsAdapter);
        // Views
        binding.tvNumSteps.setText(String.valueOf(stepsAdapter.getItemCount()));
        binding.tvNumIngred.setText(String.valueOf(ingredientsAdapter.getItemCount()));
        binding.tvRecipeName.setText(recipes.getName());
        displayRecipeImage();
    }


    private void displayRecipeImage() {
        switch (recipes.getId()) {
            case 1:
                binding.image.setImageResource(R.drawable.nut);
                break;
            case 2:
                binding.image.setImageResource(R.drawable.brown);
                break;
            case 3:
                binding.image.setImageResource(R.drawable.yellow);
                break;
            default:
                binding.image.setImageResource(R.drawable.chess);
        }
    }
}