package com.example.android.bakingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.model.Ingredient;
import com.example.android.bakingapp.databinding.LayoutIngredientsBinding;
import com.example.android.bakingapp.ui.adapter.viewholder.IngredientsViewHolder;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    private final List<Ingredient> ingredientList;
    // Constructor for our IngredientsAdapter
    public IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }


    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(LayoutIngredientsBinding
                .inflate(LayoutInflater.from(parent.getContext())));
        // To adjust the size of CardView
        viewHolder.itemView.setLayoutParams(new RecyclerView
                .LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}