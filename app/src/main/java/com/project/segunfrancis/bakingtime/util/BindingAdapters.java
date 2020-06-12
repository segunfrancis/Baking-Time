package com.project.segunfrancis.bakingtime.util;

import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.ui.MainActivity;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public final class BindingAdapters {

    @BindingAdapter("populateRecyclerView")
    public static void bindAdapter(RecyclerView view, List<Recipe> recipes) {
        RecipeAdapter adapter = new RecipeAdapter(recipes, );
        view.setAdapter(adapter);
    }
}
