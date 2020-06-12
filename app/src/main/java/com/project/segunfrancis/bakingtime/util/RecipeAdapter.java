package com.project.segunfrancis.bakingtime.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> list;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(List<Recipe> list,
                         OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final Recipe model,
                         final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());
                }
            });
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
        Recipe item = list.get(position);
        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}