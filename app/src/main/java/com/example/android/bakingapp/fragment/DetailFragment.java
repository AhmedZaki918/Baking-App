package com.example.android.bakingapp.fragment;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private Step mStep;
    private String mDescription;
    private String videoUrl;
    private TextView videoDescription;

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;

    Bundle bundle;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        videoDescription = view.findViewById(R.id.tv_description);
        exoPlayerView = view.findViewById(R.id.exo_player_view);


        bundle = this.getArguments();
        if (bundle != null) {
            mStep = bundle.getParcelable(Constant.STEPS);

            mDescription = mStep.getDescription();
            videoDescription.setText(mDescription);

            displayVideo();
        }

        return view;
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
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

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
