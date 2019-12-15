package com.example.jetpackcomponentsapp

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class BottomOffsetDecorationHelper : RecyclerView.ItemDecoration {

    private var bottomItemOffset: Int? = null

    constructor(@NonNull context: Context, @DimenRes itemOffsetId: Int) {
        bottomItemOffset = itemOffsetId
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dataSize = state.itemCount
        val position = parent.getChildAdapterPosition(view)
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, bottomItemOffset?:0)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }
}