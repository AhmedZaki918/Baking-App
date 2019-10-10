package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.Constant;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    // Variables for steps data
    private Step mStep;
    private String mDescription;
    private String videoUrl;
    @BindView(R.id.tv_description)
    TextView videoDescription;

    // Views for Exoplayer
    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView exoPlayerView;
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
        mDescription = mStep.getDescription();
        videoDescription.setText(mDescription);

        // Calling the method
        displayVideo();
    }

    // Release mediaplayer
    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releaseMediaPlayer();
        }
    }

    // Displays the video on the instructions screen
    private void displayVideo() {
        // Get video url
        videoUrl = mStep.getVideoURL();

        // Don't run the video If the url is empty
        if (videoUrl.equals("")) {
            exoPlayerView.setVisibility(View.GONE);

        } else {
            // Run the video if the url it's not empty
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                // Parse the url of the video
                Uri videoURI = Uri.parse(videoUrl);
                // Setup for media source
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                // Prepare the Exoplayer
                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    // Release the media
    private void releaseMediaPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
    }
}