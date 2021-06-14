package com.example.android.bakingapp.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class ViewUtils {

    @SuppressWarnings("rawtypes")
    public static void setupRecyclerView(RecyclerView recyclerView,
                                         LinearLayoutManager layoutManager,
                                         RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}