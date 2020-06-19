package com.project.segunfrancis.bakingtime.widget;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.util.ArrayList;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_FROM_ACTIVITY_INGREDIENTS_LIST;
import static com.project.segunfrancis.bakingtime.util.AppConstants.WIDGET_INTENT_KEY;

public class BakingService extends IntentService {

    public BakingService() {
        super("BakingService");
    }

    public static void actionStartBakingService(Context context, ArrayList<String> fromActivityIngredientList) {
        Intent intent = new Intent(context, BakingService.class);
        intent.putExtra(INTENT_FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientList = intent.getExtras().getStringArrayList(INTENT_FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionBakingWidgetUpdate(fromActivityIngredientList);
        }
    }

    private void handleActionBakingWidgetUpdate(ArrayList<String> fromActivityIngredientList) {
        Intent intent = new Intent(WIDGET_INTENT_KEY);
        intent.setAction(WIDGET_INTENT_KEY);
        intent.putExtra(INTENT_FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientList);
        sendBroadcast(intent);
    }
}
