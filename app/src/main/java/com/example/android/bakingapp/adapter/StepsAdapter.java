package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.DetailsActivity;
import com.example.android.bakingapp.activity.VideoActivity;
import com.example.android.bakingapp.fragment.DetailFragment;
import com.example.android.bakingapp.model.Step;

import java.util.List;

/**
 * An {@link StepsAdapter} knows how to create a list item layout for each recipe
 * in the data source (a list of {@link Step} objects).
 * <p>
 * These list item layouts will be provided to an adapter
 * to be displayed to the user.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private Context context;
    private List<Step> mStepList;

    // Constructor for our StepsAdapter
    public StepsAdapter(Context context, List<Step> mStepList) {
        this.context = context;
        this.mStepList = mStepList;
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

        View view = LayoutInflater.from(context).inflate(R.layout.layout_steps, null, false);
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
        final Step currentItem = mStepList.get(position);
        // Set the given text on view
        holder.shortDescription.setText(currentItem.getShortDescription());

        // Add 0 before the numbers from ( 0 : 9 ) only
        int convertedId = Integer.parseInt(currentItem.getId());
        // Set the number of step on view
        if (convertedId >= 10) {
            holder.stepsNum.setText(currentItem.getId());
        } else {
            holder.stepsNum.setText(String.format("0%s", currentItem.getId()));
        }

        // Set on click listener on the view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Tablet case
                if (isTablet(context)) {
                    DetailsActivity detailsActivity = (DetailsActivity) context;
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.STEPS, currentItem);
                    detailFragment.setArguments(bundle);
                    replace(detailFragment, detailsActivity.getSupportFragmentManager().beginTransaction());

                    // Mobile case
                } else {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra(Constant.STEPS, currentItem);
                    context.startActivity(intent);
                }
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
        return mStepList != null ? mStepList.size() : 0;
    }

    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Initialize the view
         */
        private TextView shortDescription;
        private TextView stepsNum;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextView
         *
         * @param itemView The View that you inflated in
         *                 {@link StepsAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(View itemView) {
            super(itemView);
            shortDescription = itemView.findViewById(R.id.tv_short_description);
            stepsNum = itemView.findViewById(R.id.tv_num_steps);
        }
    }

    /**
     * Helper method for fragment
     *
     * @param fragment            is the fragment itself
     * @param fragmentTransaction is the transaction of the fragment
     */
    private void replace(Fragment fragment, FragmentTransaction fragmentTransaction) {
//        fragmentTransaction.replace(R.id.detailContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * If the device is tablet
     *
     * @param context the current context of the app
     * @return if the device is tablet or mobile
     */
    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration()
                .screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}