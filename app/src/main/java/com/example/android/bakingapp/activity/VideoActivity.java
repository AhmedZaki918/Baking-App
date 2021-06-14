package com.example.android.bakingapp.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.adapter.Constant;
import com.example.android.bakingapp.databinding.ActivityVideoBinding;
import com.example.android.bakingapp.data.model.Step;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VideoActivity extends AppCompatActivity {

    /**
     * Initialization
     */
    private ActivityVideoBinding binding;
    private Step mStep;
    private SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mStep = getIntent().getParcelableExtra(Constant.STEPS);
        displayVideo();
        if (mStep != null) {
            binding.tvDescription.setText(mStep.getDescription());
            binding.tvStepId.setText(String.format("#%s", mStep.getId()));
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }


    private void displayVideo() {
        // Don't run the video If the url is empty
        if (mStep.getVideoURL().equals("")) {
            binding.exoPlayerView.setVisibility(GONE);
            binding.ivAlternative.setVisibility(VISIBLE);
            displayImages();

        } else {
            // Run the video if the url it's not empty
            exoPlayer = new SimpleExoPlayer.Builder(this).build();
            binding.exoPlayerView.setPlayer(exoPlayer);
            // This is the source of media to be played.
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(mStep.getVideoURL())));
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.prepare();
        }
    }


    // Display random icons for each step instead of "No Video Case"
    private void displayImages() {
        int[] recipesIcons = {R.drawable.nutella_icon, R.drawable.cheesecake_icon,
                R.drawable.brownies_icon, R.drawable.yellowcake_icon};
        // Displays the image on view
        binding.ivAlternative.setImageResource(recipesIcons[new Random().nextInt(recipesIcons.length)]);
    }
}