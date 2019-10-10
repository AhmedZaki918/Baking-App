package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.MainActivity;


/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
            views.setRemoteAdapter(R.id.widget_list,
                    BakingWidgetService.getIntent(context));
            views.setPendingIntentTemplate(R.id.widget_list, pendingIntent);
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}