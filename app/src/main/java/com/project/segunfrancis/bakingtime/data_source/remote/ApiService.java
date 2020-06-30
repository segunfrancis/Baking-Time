package com.project.segunfrancis.bakingtime.data_source.remote;

import com.project.segunfrancis.bakingtime.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SegunFrancis
 */

public interface ApiService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipe();
}
