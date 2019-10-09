package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


public class DisplayIngredientsService extends IntentService {


    public static final String ACTION_UPDATE_INGREDIENTS_WIDGETS =
            "com.example.android.bakingapp.action.update_ingredients_widget";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DisplayIngredientsService() {
        super("DisplayIngredientsService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS_WIDGETS.equals(action)) {


//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
//                IngredientsWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds, );
            }
        }
    }
}
