package com.example.android.bakingapp.ui.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.databinding.LayoutMainBinding;
import com.example.android.bakingapp.util.OnAdapterClick;

public class MainViewHolder extends RecyclerView.ViewHolder {

    LayoutMainBinding binding;
    OnAdapterClick onAdapterClick;


    // Our constructor
    public MainViewHolder(LayoutMainBinding binding, OnAdapterClick onAdapterClick) {
        super(binding.getRoot());
        this.binding = binding;
        this.onAdapterClick = onAdapterClick;
    }


    public void bind(Recipe recipe) {
        binding.tvRecipeName.setText(recipe.getName());
        // Displays recipe image on the screen by it's id
        switch (recipe.getId()) {
            case 1:
                binding.ivRecipe.setImageResource(R.drawable.nut);
                binding.tvCaption.setText(R.string.nutella);
                break;
            case 2:
                binding.ivRecipe.setImageResource(R.drawable.brown);
                binding.tvCaption.setText(R.string.brownies);
                break;
            case 3:
                binding.ivRecipe.setImageResource(R.drawable.yellow);
                binding.tvCaption.setText(R.string.yellow_cake);
                break;
            default:
                binding.ivRecipe.setImageResource(R.drawable.chess);
                binding.tvCaption.setText(R.string.cheescake);
        }
        itemView.setOnClickListener(v -> onAdapterClick.onItemClick(recipe));
    }
}