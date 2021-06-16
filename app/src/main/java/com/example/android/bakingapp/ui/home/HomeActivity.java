package com.example.android.bakingapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.ui.adapter.Constant;
import com.example.android.bakingapp.ui.adapter.MainAdapter;
import com.example.android.bakingapp.ui.details.DetailsActivity;
import com.example.android.bakingapp.util.OnAdapterClick;
import com.example.android.bakingapp.util.ViewUtils;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

import static android.view.View.GONE;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity implements OnAdapterClick {


    private ActivityMainBinding binding;
    HomeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.initRequest();
        viewModel.getResponse().observe(this, this::updateUi);
    }


    private void updateUi(List<Recipe> data) {
        ViewUtils.setupRecyclerView(binding.recyclerView,
                new LinearLayoutManager(this),
                new MainAdapter(data, this));
        binding.progressBar.setVisibility(GONE);
    }


    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constant.RECIPE, recipe);
        this.startActivity(intent);
    }
}