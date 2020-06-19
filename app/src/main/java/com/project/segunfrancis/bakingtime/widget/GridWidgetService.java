package com.project.segunfrancis.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.segunfrancis.bakingtime.R;

import java.util.List;

import static com.project.segunfrancis.bakingtime.widget.BakingTimeWidgetProvider.ingredientsList;

/**
 * Created by SegunFrancis
 */
public class GridWidgetService extends RemoteViewsService {
    List<String> remoteIngredientList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteIngredientList = ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteIngredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            remoteViews.setTextViewText(R.id.widget_grid_view_item, remoteIngredientList.get(position));
            Intent fillInIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);
            return remoteViews;
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
    }
}