package com.example.android.bakingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.DetailsActivity;
import com.example.android.bakingapp.data.model.Recipe;

import java.util.List;


/**
 * An {@link MainAdapter} knows how to create a list item layout for each recipe
 * in the data source (a list of {@link Recipe} objects).
 * <p>
 * These list item layouts will be provided to an adapter
 * to be displayed to the user.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> mRecipeList;

    // Constructor for our MainAdapter
    public MainAdapter(Context context, List<Recipe> mRecipeList) {
        this.context = context;
        this.mRecipeList = mRecipeList;
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main, null, false);
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
        final Recipe currentItem = mRecipeList.get(position);

        // Set the given text on the view
        holder.recipeName.setText(currentItem.getName());
        // Displays recipe image on the screen by it's id
        switch (currentItem.getId()) {
            case 1:
                holder.recipePhoto.setImageResource(R.drawable.nut);
                holder.recipeCaption.setText(R.string.nutella);
                break;
            case 2:
                holder.recipePhoto.setImageResource(R.drawable.brown);
                holder.recipeCaption.setText(R.string.brownies);
                break;
            case 3:
                holder.recipePhoto.setImageResource(R.drawable.yellow);
                holder.recipeCaption.setText(R.string.yellow_cake);
                break;
            default:
                holder.recipePhoto.setImageResource(R.drawable.chess);
                holder.recipeCaption.setText(R.string.cheescake);
        }

        // Set on click listener on the view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setup the intent to go to the DetailsActivity
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constant.RECIPE, currentItem);
                context.startActivity(intent);
            }
        });
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our recipes
     */
    @Override
    public int getItemCount() {
        return mRecipeList != null ? mRecipeList.size() : 0;
    }

    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Initialize the view
         */
        private TextView recipeName;
        private ImageView recipePhoto;
        private TextView recipeCaption;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextView
         *
         * @param itemView The View that you inflated in
         *                 {@link MainAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            recipePhoto = itemView.findViewById(R.id.iv_recipe);
            recipeCaption = itemView.findViewById(R.id.tv_caption);
        }
    }
}