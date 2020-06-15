package com.project.segunfrancis.bakingtime.ui.main;

import android.util.Log;

import com.project.segunfrancis.bakingtime.data_source.ApiService;
import com.project.segunfrancis.bakingtime.data_source.RetrofitClient;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.util.State;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.project.segunfrancis.bakingtime.util.AppConstants.isConnectionAvailable;

/**
 * Created by SegunFrancis
 */
public class MainViewModel extends ViewModel {

    MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();
    MutableLiveData<String> message = new MutableLiveData<>();
    MutableLiveData<State> state = new MutableLiveData<>();

    public MainViewModel() {
        getRecipes();
    }

    private void getRecipes() {
        state.setValue(State.LOADING);
        message.setValue("Loading...");
        ApiService service = RetrofitClient.getClient().create(ApiService.class);
        service.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipe>> call, @NotNull Response<List<Recipe>> response) {
                recipeList.setValue(response.body());
                state.setValue(State.SUCCESS);
                message.setValue("Success");
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipe>> call, @NotNull Throwable t) {
                state.setValue(State.ERROR);
                message.setValue(t.getLocalizedMessage());
                Log.e("MainResponse", t.getLocalizedMessage());
            }
        });
    }

    void loadRecipes() {
        getRecipes();
    }
}
