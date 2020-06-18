package com.project.segunfrancis.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Recipe;

/**
 * Created by SegunFrancis
 */
public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory((Recipe) intent.getSerializableExtra("to_be_implemented"), this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Recipe mRecipe;
    private Context mContext;

    public GridRemoteViewsFactory(Recipe recipe, Context context) {
        mRecipe = recipe;
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingTimeWidgetProvider.class));
        BakingTimeWidgetProvider.updateIngredientWidget(mContext, appWidgetManager, mRecipe, ids);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipe == null) return 0;
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        //String recipeName = mRecipe.getName();
        String ingredientName = mRecipe.getIngredients().get(mRecipe.getId()).getIngredient();
        String ingredientQuantity = String.valueOf(mRecipe.getIngredients().get(mRecipe.getId()).getQuantity());
        String ingredientMeasure = mRecipe.getIngredients().get(mRecipe.getId()).getMeasure();
        String ingredientDisplay = ingredientQuantity + " " + ingredientMeasure;
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.baking_time_widget);
        //remoteViews.setTextViewText(R.id.widget_recipe_name, recipeName);
        remoteViews.setTextViewText(R.id.ingredient_name, ingredientName);
        remoteViews.setTextViewText(R.id.ingredient_quantity, ingredientDisplay);
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}