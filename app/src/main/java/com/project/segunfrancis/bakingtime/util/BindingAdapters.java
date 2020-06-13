package com.project.segunfrancis.bakingtime.util;


import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.segunfrancis.bakingtime.model.Ingredient;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public final class BindingAdapters {

    @BindingAdapter("loadImage")
    public static void loadImages(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter("loadIngredients")
    public static void loadIngredient(RecyclerView view, List<Ingredient> ingredients) {
        IngredientsAdapter adapter = new IngredientsAdapter(ingredients);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.HORIZONTAL));
    }
}
