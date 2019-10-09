package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    Context context;
    private List<Ingredient> mIngredientList;

    public IngredientsAdapter(Context context, List<Ingredient> mIngredientList) {
        this.context = context;
        this.mIngredientList = mIngredientList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_ingredients, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Ingredient currentItem = mIngredientList.get(position);
        holder.ingredient.setText(currentItem.getIngredient());
        holder.measure.setText(currentItem.getMeasure());
        holder.quantity.setText(currentItem.getQuantity());

    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView quantity;
        TextView measure;
        TextView ingredient;


        public ViewHolder(View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.tv_quantity);
            measure = itemView.findViewById(R.id.tv_measure);
            ingredient = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}