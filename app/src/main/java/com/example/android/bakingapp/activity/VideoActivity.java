package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.Constant;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    // Variables for steps data
    private Step mStep;
    @SuppressWarnings("FieldCanBeLocal")
    private String mDescription;
    @SuppressWarnings("FieldCanBeLocal")
    private String videoUrl;
    @BindView(R.id.tv_description)
    TextView videoDescription;
    @BindView(R.id.tv_step_id)
    TextView stepId;
    @BindView(R.id.constraint_layout)
    ConstraintLayout cl;
    @BindView(R.id.iv_alternative)
    ImageView ivVideo;

    // Views for Exoplayer
    @BindView(R.id.exo_player_view)
    PlayerView playerView;
    private SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        // Get data from intent
        Intent intent = getIntent();
        mStep = intent.getParcelableExtra(Constant.STEPS);

        //Displays description for the video
        assert mStep != null;
        mDescription = mStep.getDescription();
        videoDescription.setText(mDescription);

        // Calling the method
        displayVideo();
        stepId.setText(String.format("#%s", mStep.getId()));
    }

    // Release mediaplayer
    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releaseMediaPlayer();
        }
    }

    /**
     * Displays the video on the instructions screen
     */
    private void displayVideo() {
        // Get video url
        videoUrl = mStep.getVideoURL();

        // Don't run the video If the url is empty
        if (videoUrl.equals("")) {
            playerView.setVisibility(View.GONE);
            ivVideo.setVisibility(View.VISIBLE);

            // an array of images to display random icons for each step instead of "No Video Case"
            int[] recipesIcons = {R.drawable.nutella_icon,
                    R.drawable.cheesecake_icon,
                    R.drawable.brownies_icon,
                    R.drawable.yellowcake_icon};
            // Displays the image on view
            ivVideo.setImageResource(recipesIcons[new Random().nextInt(recipesIcons.length)]);

        } else {
            // Run the video if the url it's not empty
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                // Parse the url of the video
                Uri videoURI = Uri.parse(videoUrl);

                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                        Util.getUserAgent(this, "applicationName"));
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(videoURI);

                // Prepare the Exoplayer
                playerView.setPlayer(exoPlayer);
                exoPlayer.prepare(videoSource);
                exoPlayer.setPlayWhenReady(true);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**
     * Release the media
     */
    private void releaseMediaPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
    }
}