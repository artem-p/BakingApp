package ru.artempugachev.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.ui.activity.WidgetConfigurationActivity;
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
            widgetServiceIntent.putExtra(MainActivity.RECIPE_ID_EXTRA, recipeId);
            remoteViews.setRemoteAdapter(R.id.ingredients_widget_ingredients_list, widgetServiceIntent);

            // create an intent to launch activity where one can choose recipe
            Intent recipeChooseIntent = new Intent(context, WidgetConfigurationActivity.class);
            recipeChooseIntent.putExtra(MainActivity.RECIPE_IN_WIDGET_KEY + widgetId, recipeId);
            recipeChooseIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent recipeChoosePendingIntent = PendingIntent.getActivity(context, 0, recipeChooseIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.ingredients_widget_root_layout, recipeChoosePendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, IngredientsWidgetProvider.class);
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

        int recipeId = sharedPreferences.getInt(recipeInWidgetKey, 0);  // display first recipe by default
        return recipeId;
    }

    //
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
//        update(context, appWidgetManager, widgetIds, context.getResources().getString(R.string.ingredients_widget_default_text));
//    }
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.hasExtra(WIDGET_IDS_EXTRA)) {
//            int[] widgetIds = intent.getExtras().getIntArray(WIDGET_IDS_EXTRA);
//            if (intent.hasExtra(INGREDIENTS_TEXT_EXTRA)) {
//                String ingredientsList = intent.getStringExtra(INGREDIENTS_TEXT_EXTRA);
//                update(context, AppWidgetManager.getInstance(context), widgetIds, ingredientsList);
//            } else {
//                onUpdate(context, AppWidgetManager.getInstance(context), widgetIds);
//            }
//        } else super.onReceive(context, intent);
//    }
//
//
//    private void update(Context context, AppWidgetManager appWidgetManager, int[] widgetIds, String ingredients) {
//        for (int widgetId : widgetIds) {
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
//            views.setTextViewText(R.id.ingredients_widget_list, ingredients);
//            appWidgetManager.updateAppWidget(widgetId, views);
//        }
//    }
}
