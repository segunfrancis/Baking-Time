package com.project.segunfrancis.bakingtime.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.segunfrancis.bakingtime.model.Recipe;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import com.project.segunfrancis.bakingtime.R;

/**
 * Created by SegunFrancis
 */
public class RecipeAdapter extends
        RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipeList;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener onItemClickListener) {
        this.mRecipeList = recipes;
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(final Recipe recipe,
                  final OnItemClickListener listener) {
            TextView textView = itemView.findViewById(R.id.recipe_name_textView);
            textView.setText(recipe.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(recipe));
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_recipe_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe item = mRecipeList.get(position);
        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }
}