package com.example.android.bakingapp.ui.video;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.model.Step;
import com.example.android.bakingapp.databinding.ActivityVideoBinding;
import com.example.android.bakingapp.ui.adapter.Constant;
import com.example.android.bakingapp.util.ViewUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VideoActivity extends AppCompatActivity {


    //  Initialization
    private ActivityVideoBinding binding;
    private Step steps;
    private SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        steps = getIntent().getParcelableExtra(Constant.STEPS);
        updateUi();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }


    private void updateUi() {
        if (steps != null) {
            // Update text views
            binding.tvDescription.setText(steps.getDescription());
            binding.tvStepId.setText(String.format("#%s", steps.getId()));
            // Don't run the video If the url is empty
            if (steps.getVideoURL().equals("")) {
                binding.exoPlayerView.setVisibility(GONE);
                binding.ivAlternative.setVisibility(VISIBLE);
                displayImages();
            } else {
                // Run the video
                exoPlayer = new SimpleExoPlayer.Builder(this).build();
                ViewUtils.setupExoPlayer(exoPlayer, binding.exoPlayerView, steps.getVideoURL());
            }
        }
    }


    // Display random icons for each step instead of "No Video Case"
    private void displayImages() {
        int[] recipesIcons = {R.drawable.nutella_icon, R.drawable.cheesecake_icon,
                R.drawable.brownies_icon, R.drawable.yellowcake_icon};
        // Displays the image on view
        binding.ivAlternative
                .setImageResource(recipesIcons[new Random().nextInt(recipesIcons.length)]);
    }
}