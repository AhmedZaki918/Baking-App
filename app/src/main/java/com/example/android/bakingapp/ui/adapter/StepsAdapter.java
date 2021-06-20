package com.example.android.bakingapp.ui.adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.bakingapp.databinding.LayoutStepsBinding;
import com.example.android.bakingapp.ui.adapter.viewholder.StepsViewHolder;
import com.example.android.bakingapp.data.model.Step;
import com.example.android.bakingapp.util.OnStepClick;

import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private final List<Step> stepList;
    private final OnStepClick onStepClick;
    // Constructor for our StepsAdapter
    public StepsAdapter(List<Step> stepList, OnStepClick onStepClick) {
        this.stepList = stepList;
        this.onStepClick = onStepClick;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepsViewHolder(LayoutStepsBinding
                .inflate(LayoutInflater.from(parent.getContext())), onStepClick);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(stepList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }
}