package com.project.segunfrancis.bakingtime.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Step;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public class StepAdapter extends
        RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps;
    private OnStepItemClickListener onItemClickListener;

    public StepAdapter(List<Step> steps,
                       OnStepItemClickListener onItemClickListener) {
        this.steps = steps;
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Step step, OnStepItemClickListener listener) {
            TextView stepText = itemView.findViewById(R.id.step_textView);
            ImageView thumbnail = itemView.findViewById(R.id.thumbnail_imageView);
            Glide.with(thumbnail.getContext()).load(step.getThumbnailURL())
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.recipe_image)
                    .into(thumbnail);
            stepText.setText(step.getShortDescription());
            stepText.setOnClickListener(v -> listener.onStepItemClick(step));
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_layout_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step item = steps.get(position);
        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        else
            return steps.size();
    }

    public interface OnStepItemClickListener {
        void onStepItemClick(Step step);
    }
}