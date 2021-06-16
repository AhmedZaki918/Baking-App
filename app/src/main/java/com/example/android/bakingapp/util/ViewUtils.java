package com.example.android.bakingapp.util;

import android.net.Uri;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public final class ViewUtils {


    @SuppressWarnings("rawtypes")
    public static void setupRecyclerView(RecyclerView recyclerView,
                                         LinearLayoutManager layoutManager,
                                         RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    public static void setupExoPlayer(SimpleExoPlayer exoPlayer,
                                      PlayerView playerView,
                                      String url) {
        playerView.setPlayer(exoPlayer);
        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(url)));
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.prepare();
    }
}