package ru.artempugachev.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ru.artempugachev.bakingapp.R;

/**
 * Provider for ingredients list widget
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {
    public static final String WIDGET_IDS_EXTRA = "widget_ids";
    public static final String INGREDIENTS_TEXT_EXTRA = "ingredients_text";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
        update(context, appWidgetManager, widgetIds, context.getResources().getString(R.string.ingredients_widget_default_text));
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }


    private void update(Context context, AppWidgetManager appWidgetManager, int[] ids, String ingredients) {
        for (int widgetId : ids) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            views.setTextViewText(R.id.ingredients_widget_list, ingredients);
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }
}
