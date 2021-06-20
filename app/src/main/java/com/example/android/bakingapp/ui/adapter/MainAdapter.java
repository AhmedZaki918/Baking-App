package com.example.android.bakingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.databinding.LayoutMainBinding;
import com.example.android.bakingapp.ui.adapter.viewholder.MainViewHolder;
import com.example.android.bakingapp.util.OnAdapterClick;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private final List<Recipe> recipeList;
    private final OnAdapterClick onAdapterClick;
    // Constructor for our MainAdapter
    public MainAdapter(List<Recipe> recipeList, OnAdapterClick onAdapterClick) {
        this.recipeList = recipeList;
        this.onAdapterClick = onAdapterClick;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutMainBinding
                .inflate(LayoutInflater.from(parent.getContext())), onAdapterClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}