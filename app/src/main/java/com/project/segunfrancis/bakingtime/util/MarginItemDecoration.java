package com.project.segunfrancis.bakingtime.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 *
 * Custom class that creates even spaces between each item
 */

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceHeight;

    public MarginItemDecoration(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spaceHeight;
            outRect.left = spaceHeight;
            outRect.right = spaceHeight;
            outRect.bottom = spaceHeight;
        }
        outRect.left = spaceHeight;
        outRect.right = spaceHeight;
        outRect.bottom = spaceHeight;
    }
}
