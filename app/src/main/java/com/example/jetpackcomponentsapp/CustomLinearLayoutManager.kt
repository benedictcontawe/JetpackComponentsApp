package com.example.jetpackcomponentsapp

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager : LinearLayoutManager {

    companion object {
        private var TAG: String = CustomLinearLayoutManager::class.java.simpleName
    }

    constructor(context : Context) : super(context) {

    }

    constructor(context : Context, orientation : Int, reverseLayout : Boolean) : super(context, orientation, reverseLayout){

    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes){

    }

    override fun onLayoutChildren(recycler : RecyclerView.Recycler?, state : RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (ex : IndexOutOfBoundsException) {
            Log.e(TAG, "Catch ${ex.message}")
        }
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        //super.smoothScrollToPosition(recyclerView, state, position)
        Log.d(CustomLinearLayoutManager::class.java.getSimpleName(), "smoothScrollToPosition()")
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.getContext()) {
            private val MILLISECONDS_PER_INCH = 500f
            private val DISTANCE_IN_PIXELS = 500f
            private val DURATION = 500f

            override fun getHorizontalSnapPreference(): Int {
                return super.getHorizontalSnapPreference()
            }

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@CustomLinearLayoutManager
                        .computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                val proportion = dx.toFloat() / DISTANCE_IN_PIXELS
                return (DURATION * proportion).toInt()
            }

            override fun calculateSpeedPerPixel(displayMetrics : DisplayMetrics) : Float {
                //return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                return super.calculateSpeedPerPixel(displayMetrics)
            }

        }

        linearSmoothScroller.computeScrollVectorForPosition(position);
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}