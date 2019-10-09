package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
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

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private static final String LOG_TAG = StepsAdapter.class.getSimpleName();


    Context context;
    private List<Step> mStepList;

    public StepsAdapter(Context context, List<Step> mStepList) {
        this.context = context;
        this.mStepList = mStepList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_steps, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Step currentItem = mStepList.get(position);
        holder.shortDescription.setText(currentItem.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTablet(context)) {
                    DetailsActivity detailsActivity = (DetailsActivity) context;
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.STEPS, currentItem);
                    detailFragment.setArguments(bundle);
                    replace(detailFragment, R.id.detailContainer, detailsActivity.getSupportFragmentManager().beginTransaction());

                } else {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra(Constant.STEPS, currentItem);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStepList != null ? mStepList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shortDescription;


        public ViewHolder(View itemView) {
            super(itemView);

            shortDescription = itemView.findViewById(R.id.tv_short_description);
        }
    }

    private void replace(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
