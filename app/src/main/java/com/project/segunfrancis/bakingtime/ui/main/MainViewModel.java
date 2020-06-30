package com.project.segunfrancis.bakingtime.ui.main;

import android.os.Looper;
import android.util.Log;

import com.project.segunfrancis.bakingtime.data_source.remote.ApiService;
import com.project.segunfrancis.bakingtime.data_source.remote.RetrofitClient;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.util.State;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SegunFrancis
 */
public class MainViewModel extends ViewModel {

    public MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();
    MutableLiveData<String> message = new MutableLiveData<>();
    MutableLiveData<State> state = new MutableLiveData<>();

    public MainViewModel() {
        getRecipes();
    }

    private void getRecipes() {
        if (isMainThread()) {
            state.setValue(State.LOADING);
            message.setValue("Loading...");
        }
        else {
            state.postValue(State.LOADING);
            message.postValue("Loading...");
        }
        ApiService service = RetrofitClient.getClient().create(ApiService.class);
        service.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipe>> call, @NotNull Response<List<Recipe>> response) {
                if (isMainThread()) {
                    recipeList.setValue(response.body());
                    state.setValue(State.SUCCESS);
                    message.setValue("Success");
                } else {
                    recipeList.postValue(response.body());
                    state.postValue(State.SUCCESS);
                    message.postValue("Success");
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipe>> call, @NotNull Throwable t) {
                if (isMainThread()) {
                    state.setValue(State.ERROR);
                    message.setValue(t.getLocalizedMessage());
                } else {
                    state.postValue(State.ERROR);
                    message.postValue(t.getLocalizedMessage());
                }
                Log.e("MainResponse", t.getLocalizedMessage());
            }
        });
    }

    void loadRecipes() {
        getRecipes();
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
