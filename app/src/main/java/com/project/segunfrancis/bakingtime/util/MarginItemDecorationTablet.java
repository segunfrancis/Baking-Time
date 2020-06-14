package com.project.segunfrancis.bakingtime.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 * <p>
 * Custom class that creates even spaces between each item
 */

@SuppressWarnings("ALL")
public class MarginItemDecorationTablet extends RecyclerView.ItemDecoration {
    private int spaceHeight;

    public MarginItemDecorationTablet(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (parent.getChildAdapterPosition(view) == 0
                || parent.getChildAdapterPosition(view) == 1
                || parent.getChildAdapterPosition(view) == 2) {
            outRect.top = spaceHeight;
        }
        if (parent.getChildAdapterPosition(view) == 0
                || parent.getChildAdapterPosition(view) == 1
                || parent.getChildAdapterPosition(view) % 2 == 0
                || parent.getChildAdapterPosition(view) % 3 == 0) {
            outRect.right = spaceHeight;
        }
        outRect.left = spaceHeight;
        outRect.bottom = spaceHeight;
    }
}
