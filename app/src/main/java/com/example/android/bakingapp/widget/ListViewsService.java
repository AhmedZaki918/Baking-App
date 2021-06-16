package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewsService implements RemoteViewsService.RemoteViewsFactory {


    private final Context context;
    private List<String> mIngredientsInPref;

    ListViewsService(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        mIngredientsInPref = getPrefs(context);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (mIngredientsInPref == null) {
            return 0;
        }
        return mIngredientsInPref.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.layout_widget);
        String ing = mIngredientsInPref.get(position);
        remoteView.setTextViewText(R.id.widget_item, ing);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    /**
     * Setup preferences for widget
     *
     * @param mContext the current context
     * @return array list of String
     */
    private ArrayList<String> getPrefs(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("appWidget", 0);
        int size = prefs.getInt("ingredients" + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            array.add(prefs.getString("ingredients" + "_" + i, null));
        return array;
    }
}