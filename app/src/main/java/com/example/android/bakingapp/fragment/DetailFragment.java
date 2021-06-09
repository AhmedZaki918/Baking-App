package com.example.android.bakingapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.Constant;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    // Variables for steps data
    private Step mStep;
    @SuppressWarnings("FieldCanBeLocal")
    private String mDescription;
    @SuppressWarnings("FieldCanBeLocal")
    private String videoUrl;
    @SuppressWarnings("FieldCanBeLocal")
    private Bundle bundle;
    @BindView(R.id.tv_description)
    TextView videoDescription;
    @BindView(R.id.iv_alternative)
    ImageView ivVideo;

    // Views for Exoplayer
    @BindView(R.id.exo_player_view)
    PlayerView playerView;
    private SimpleExoPlayer exoPlayer;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        // Retrieve the steps via Bundle
        bundle = this.getArguments();
        if (bundle != null) {
            mStep = bundle.getParcelable(Constant.STEPS);

            // Display the description of the video
            assert mStep != null;
            mDescription = mStep.getDescription();
            videoDescription.setText(mDescription);

            // Calling the method
//            displayVideo();
        }
        return view;
    }

    /**
     * Release mediaplayer media
     */
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
//    private void displayVideo() {
//        // Get video url
//        videoUrl = mStep.getVideoURL();
//
//        // Don't run the video If the url is empty
//        if (videoUrl.equals("")) {
//            playerView.setVisibility(View.GONE);
//            ivVideo.setVisibility(View.VISIBLE);
//
//            // an array of images to display random icons for each step instead of "No Video Case"
//            int[] recipesIcons = {R.drawable.nutella_icon,
//                    R.drawable.cheesecake_icon,
//                    R.drawable.brownies_icon,
//                    R.drawable.yellowcake_icon};
//            // Displays the image on view
//            ivVideo.setImageResource(recipesIcons[new Random().nextInt(recipesIcons.length)]);
//
//        } else {
//            // Run the video if the url it's not empty
//            try {
//                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//                exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
//
//                // Parse the url of the video
//                Uri videoURI = Uri.parse(videoUrl);
//                // Setup for media source
//                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
//                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//                MediaSource mediaSource = new ExtractorMediaSource(videoURI,
//                        dataSourceFactory,
//                        extractorsFactory,
//                        null,
//                        null);
//
//                // Prepare the Exoplayer
//                playerView.setPlayer(exoPlayer);
//                exoPlayer.prepare(mediaSource);
//                exoPlayer.setPlayWhenReady(true);
//
//            } catch (Exception e) {
//                e.getMessage();
//            }
//        }
//    }

    /**
     * Release the media
     */
    private void releaseMediaPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
    }
}