package com.example.android.bakingapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.Constant;
import com.example.android.bakingapp.databinding.ActivityVideoBinding;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.Random;

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
        //Displays description for the video
        String mDescription = null;
        if (mStep != null) {
            mDescription = mStep.getDescription();
        }

        displayVideo();
        binding.tvDescription.setText(mDescription);
        binding.tvStepId.setText(String.format("#%s", mStep.getId()));
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
        String videoUrl = mStep.getVideoURL();
        // Don't run the video If the url is empty
        if (videoUrl.equals("")) {
            binding.exoPlayerView.setVisibility(View.GONE);
            binding.ivAlternative.setVisibility(View.VISIBLE);

            // an array of images to display random icons for each step instead of "No Video Case"
            int[] recipesIcons = {R.drawable.nutella_icon, R.drawable.cheesecake_icon,
                    R.drawable.brownies_icon, R.drawable.yellowcake_icon};
            // Displays the image on view
            binding.ivAlternative.setImageResource(recipesIcons[new Random().nextInt(recipesIcons.length)]);

        } else {
            // Run the video if the url it's not empty
            exoPlayer = new SimpleExoPlayer.Builder(this).build();
            binding.exoPlayerView.setPlayer(exoPlayer);
            // This is the source of media to be played.
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)));
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.prepare();
        }
    }
}