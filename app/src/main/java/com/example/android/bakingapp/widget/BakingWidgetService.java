package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;


public class BakingWidgetService extends RemoteViewsService {

    public static Intent getIntent(Context context) {
        return new Intent(context, BakingWidgetService.class);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListViewsService(this.getApplicationContext()));
    }
}