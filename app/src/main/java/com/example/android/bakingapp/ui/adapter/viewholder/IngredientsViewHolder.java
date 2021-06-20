package com.example.android.bakingapp.ui.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.model.Ingredient;
import com.example.android.bakingapp.databinding.LayoutIngredientsBinding;

import java.util.Random;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    LayoutIngredientsBinding binding;


    public IngredientsViewHolder(LayoutIngredientsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind(Ingredient currentItem) {
        // Set the given text on views
        binding.tvIngredient.setText(currentItem.getIngredient());
        binding.tvMeasure.setText(currentItem.getMeasure());
        binding.tvQuantity.setText(currentItem.getQuantity());

        // an array of images to display random icons for each row in the list
        int[] ingredientsIcons = {R.drawable.eggs,
                R.drawable.ingred,
                R.drawable.pot,
                R.drawable.herbs,
                R.drawable.ingred_icon};
        // Set the image on the view
        binding.ivIcon.setImageResource(ingredientsIcons[new Random().nextInt(ingredientsIcons.length)]);
    }
}
