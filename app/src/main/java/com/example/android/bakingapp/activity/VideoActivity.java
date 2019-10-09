package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class VideoActivity extends AppCompatActivity {


    private Step mStep;
    private String mDescription;
    private String videoUrl;
    private TextView videoDescription;

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        mStep = intent.getParcelableExtra(Constant.STEPS);

        videoDescription = findViewById(R.id.tv_description);
        exoPlayerView = findViewById(R.id.exo_player_view);

        mDescription = mStep.getDescription();
        videoDescription.setText(mDescription);

        displayVideo();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releaseMediaPlayer();
        }
    }

    public void displayVideo() {
        videoUrl = mStep.getVideoURL();

        if (videoUrl.equals("")) {
            exoPlayerView.setVisibility(View.GONE);

        } else {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                Uri videoURI = Uri.parse(videoUrl);

                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void releaseMediaPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
    }
}
