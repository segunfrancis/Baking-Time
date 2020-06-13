package com.project.segunfrancis.bakingtime.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Step;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public class StepAdapter extends
        RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps;
    private OnItemClickListener onItemClickListener;

    public StepAdapter(List<Step> steps,
                       OnItemClickListener onItemClickListener) {
        this.steps = steps;
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Step step, OnItemClickListener listener) {
            TextView stepText = itemView.findViewById(R.id.step_textView);
            stepText.setText(step.getShortDescription());
            itemView.setOnClickListener(v -> listener.onItemClick(step));
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
        return steps.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Step step);
    }
}