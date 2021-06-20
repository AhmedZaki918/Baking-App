package com.example.android.bakingapp.ui.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.data.model.Step;
import com.example.android.bakingapp.databinding.LayoutStepsBinding;
import com.example.android.bakingapp.util.OnStepClick;

public class StepsViewHolder extends RecyclerView.ViewHolder {

    LayoutStepsBinding binding;
    OnStepClick onStepClick;

    public StepsViewHolder(LayoutStepsBinding binding, OnStepClick onStepClick) {
        super(binding.getRoot());
        this.binding = binding;
        this.onStepClick = onStepClick;
    }


    public void bind(Step currentItem) {
        // Set the given text on views
        binding.tvShortDescription.setText(currentItem.getShortDescription());

        // Add 0 before the numbers from ( 0 : 9 ) only
        int convertedId = Integer.parseInt(currentItem.getId());
        // Set the number of step on view
        if (convertedId >= 10) binding.tvNumSteps.setText(currentItem.getId());
        else binding.tvNumSteps.setText(String.format("0%s", currentItem.getId()));
        // Click listener on the item
        itemView.setOnClickListener(v -> onStepClick.onItemClick(currentItem));
    }
}