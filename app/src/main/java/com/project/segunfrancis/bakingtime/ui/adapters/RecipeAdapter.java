package com.project.segunfrancis.bakingtime.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

        void bind(Recipe recipe, OnItemClickListener listener) {
            TextView textView = itemView.findViewById(R.id.recipe_name_textView);
            textView.setText(recipe.getName());
            ImageView imageView = itemView.findViewById(R.id.recipe_imageView);
            Glide.with(imageView.getContext())
                    .load(recipe.getImage())
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.recipe_image)
                    .into(imageView);
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
        if (mRecipeList == null)
            return 0;
        else
            return mRecipeList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }
}