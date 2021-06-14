package com.example.android.bakingapp.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.ui.adapter.MainAdapter;
import com.example.android.bakingapp.util.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static android.view.View.GONE;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {


    @Inject
    LinearLayoutManager layoutManager;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.initRequest();
        viewModel.getResponse().observe(this, this::updateUi);
    }


    private void updateUi(List<Recipe> data) {
        ViewUtils.setupRecyclerView(binding.recyclerView, layoutManager,
                new MainAdapter(HomeActivity.this, data));
        binding.progressBar.setVisibility(GONE);
    }
}