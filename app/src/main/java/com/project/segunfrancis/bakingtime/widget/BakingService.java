package com.project.segunfrancis.bakingtime.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.data_source.ApiService;
import com.project.segunfrancis.bakingtime.data_source.RetrofitClient;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.ui.details.DetailsActivity;
import com.project.segunfrancis.bakingtime.ui.main.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_ACTION_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY_RECIPE_ID;

public class BakingService extends IntentService {

    private MainViewModel mViewModel;

    public BakingService() {
        super("BakingService");
    }

    public static void actionUpdateWidget(Context context, Recipe recipe, int recipeId) {
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(INTENT_ACTION_KEY);
        intent.putExtra(INTENT_KEY, recipe);
        intent.putExtra(INTENT_KEY_RECIPE_ID, recipeId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (INTENT_ACTION_KEY.equals(action)) {
                handleActionRequestCurrentData(intent.getIntExtra(INTENT_KEY_RECIPE_ID, 0), (Recipe) intent.getSerializableExtra(INTENT_KEY));
            }
        }
    }

    private void handleActionBakingWidgetUpdate(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidget.class));

        // Trigger data update to handle the ListView
        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listView);
        // Update all widgets
        //BakingTimeWidget.updateIngredientWidget(this, appWidgetManager, recipe, appWidgetIds);
    }

    private void handleActionRequestCurrentData(int recipeId, Recipe recipe) {
        mViewModel = new MainViewModel();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidget.class));
        /*ApiService service = RetrofitClient.getClient().create(ApiService.class);
        service.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipe>> call, @NotNull Response<List<Recipe>> response) {
                Intent intent = new Intent(BakingService.this, DetailsActivity.class);
                intent.putExtra("service_to_details_activity_intent", response.body().get(recipeId));
                mViewModel.recipeList.postValue(response.body());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipe>> call, @NotNull Throwable t) {
                // TODO: handle error
            }
        });*/
        BakingTimeWidget.updateIngredientWidget(BakingService.this, appWidgetManager, recipe, appWidgetIds);
    }
}
