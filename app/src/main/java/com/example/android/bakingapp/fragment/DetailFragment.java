package com.example.android.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    // Variables for steps data
    private Step mStep;
    private String mDescription;
    private String videoUrl;
    private Bundle bundle;
    @BindView(R.id.tv_description)
    TextView videoDescription;

    // Views for Exoplayer
    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        bundle = this.getArguments();
        if (bundle != null) {
            mStep = bundle.getParcelable(Constant.STEPS);

            // Display the description of the video
            mDescription = mStep.getDescription();
            videoDescription.setText(mDescription);

            // Calling the method
            displayVideo();
        }
        return view;
    }

    // Release mediaplayer media
    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releaseMediaPlayer();
        }
    }

    // Displays the video on the instructions screen
    public void displayVideo() {
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
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

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