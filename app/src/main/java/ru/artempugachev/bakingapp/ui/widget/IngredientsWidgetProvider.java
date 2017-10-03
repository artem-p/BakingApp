package ru.artempugachev.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;

/**
 * Provider for ingredients list widget
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            int recipeId = readRecipeIdFromPrefs(context, widgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            Intent widgetServiceIntent = new Intent(context, IngredientsListService.class);

            // RemoteViewsService.onBind doesn't use intent's extra while comparing intents
            // So if we want separate RemoteViewFactories for separate widgets,
            // we need put recipe id in data, not extra
            // https://stackoverflow.com/questions/11350287/ongetviewfactory-only-called-once-for-multiple-widgets
            widgetServiceIntent.setData(Uri.fromParts("content", String.valueOf(recipeId), null));

            remoteViews.setRemoteAdapter(R.id.ingredients_widget_ingredients_list, widgetServiceIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, IngredientsWidgetProvider.class);
            int[] widgetIds = {intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)};

            this.onUpdate(context, appWidgetManager, widgetIds);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName),
                    R.id.ingredients_widget_ingredients_list);
        }
        super.onReceive(context, intent);
    }

    /**
     * Retrieve recipe id for current widget
     * */
    private int readRecipeIdFromPrefs(Context context, int widgetId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeInWidgetKey = MainActivity.RECIPE_IN_WIDGET_KEY + String.valueOf(widgetId);

        int recipeId = sharedPreferences.getInt(recipeInWidgetKey, -1);  // display first recipe by default
        return recipeId;
    }
}
