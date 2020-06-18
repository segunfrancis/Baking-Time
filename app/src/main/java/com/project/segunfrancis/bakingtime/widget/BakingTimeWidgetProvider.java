package com.project.segunfrancis.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Recipe;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY_RECIPE_ID;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidgetProvider extends AppWidgetProvider {

    private static RemoteViews getBakingGridRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_grid_view);

        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        Intent appIntent = new Intent(context, BakingService.class);
        PendingIntent appPendingIntent = PendingIntent.getService(context, 0, appIntent, 0);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        return views;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {

        Intent intent = new Intent(context, BakingService.class);
        intent.putExtra(INTENT_KEY_RECIPE_ID, recipe.getId());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);
        remoteViews.removeAllViews(R.id.widget_container);
        remoteViews.setTextViewText(R.id.widget_recipe_name, recipe.getName());
        remoteViews.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    public static void updateIngredientWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, widgetId);
        }
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
