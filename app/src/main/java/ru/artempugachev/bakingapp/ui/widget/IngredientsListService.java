package ru.artempugachev.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ru.artempugachev.bakingapp.R;

/**
 * Remote views service to display ingredients list.
 */

public class IngredientsListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewFactory(this.getApplicationContext(), intent);
    }

}

class IngredientsListViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;

    public IngredientsListViewFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 3; // todo stub
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews;

        if (position == 0) {
            // First element is recipe name
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_list_header);
            remoteViews.setTextViewText(R.id.ingredients_widget_list_header, "Recipe name");
        } else {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_list_item);
            remoteViews.setTextViewText(R.id.ingredients_widget_list_item_text, "elem");
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
