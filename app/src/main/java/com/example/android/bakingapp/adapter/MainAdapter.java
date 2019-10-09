package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.DetailsActivity;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;
    private List<Recipe> mRecipeList;

    public MainAdapter(Context context, List<Recipe> mRecipeList) {
        this.context = context;
        this.mRecipeList = mRecipeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_main, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Recipe currentItem = mRecipeList.get(position);
        holder.recipeName.setText(currentItem.getName());

        holder.recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constant.RECIPE, currentItem);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);

        }
    }
}
