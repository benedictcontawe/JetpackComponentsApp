package com.example.jetpackcomponentsapp.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BottomOffsetDecorationHelper extends RecyclerView.ItemDecoration {

    private int bottomItemOffset;

    public BottomOffsetDecorationHelper(@NonNull Context context, @DimenRes int itemOffsetId) {
        bottomItemOffset = itemOffsetId;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0,0,0,bottomItemOffset);
        } else {
          outRect.set(0,0,0,0);
        }
    }
}