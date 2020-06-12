package com.project.segunfrancis.bakingtime.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.util.Log;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.data_source.ApiService;
import com.project.segunfrancis.bakingtime.data_source.RetrofitClient;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.util.RecipeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService service = RetrofitClient.getClient().create(ApiService.class);
        service.getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipe>> call, @NotNull Response<List<Recipe>> response) {
                Log.d("MainResponse", response.body().get(1).getName());
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipe>> call, @NotNull Throwable t) {
                Log.d("MainResponse", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}
