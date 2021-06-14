package com.example.android.bakingapp.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.model.Ingredient;

import java.util.List;
import java.util.Random;


/**
 * An {@link IngredientsAdapter} knows how to create a list item layout for each recipe
 * in the data source (a list of {@link Ingredient} objects).
 * <p>
 * These list item layouts will be provided to an adapter
 * to be displayed to the user.
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context context;
    private List<Ingredient> mIngredientList;

    // Constructor for our IngredientsAdapter
    public IngredientsAdapter(Context context, List<Ingredient> mIngredientList) {
        this.context = context;
        this.mIngredientList = mIngredientList;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  Id for the list item layout
     * @return A new ViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_ingredients, null, false);
        // To adjust the size of CardView
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the position of the current list item
        Ingredient currentItem = mIngredientList.get(position);

        // Set the given text on all views from model classes
        holder.mIngredient.setText(currentItem.getIngredient());
        holder.mMeasure.setText(currentItem.getMeasure());
        holder.mQuantity.setText(currentItem.getQuantity());

        // an array of images to display random icons for each row in the list
        int[] ingredientsIcons = {R.drawable.eggs,
                R.drawable.ingred,
                R.drawable.pot,
                R.drawable.herbs,
                R.drawable.ingred_icon};
        // Set the image on the view
        holder.mIcon.setImageResource(ingredientsIcons[new Random().nextInt(ingredientsIcons.length)]);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our recipes
     */
    @Override
    public int getItemCount() {
        return mIngredientList != null ? mIngredientList.size() : 0;
    }

    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Initialize the views
         */
        private TextView mQuantity;
        private TextView mMeasure;
        private TextView mIngredient;
        private ImageView mIcon;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link IngredientsAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(View itemView) {
            super(itemView);
            mQuantity = itemView.findViewById(R.id.tv_quantity);
            mMeasure = itemView.findViewById(R.id.tv_measure);
            mIngredient = itemView.findViewById(R.id.tv_ingredient);
            mIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}