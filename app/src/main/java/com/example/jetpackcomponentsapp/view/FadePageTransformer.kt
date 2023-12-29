package com.example.jetpackcomponentsapp.view

import android.view.View
import androidx.viewpager2.widget.ViewPager2

public class FadePageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page : View, position : Float) {
        if (position <= -1.0F || position >= 1.0F) {       // [-Infinity,-1) OR (1,+Infinity]
            page.setAlpha(0.0F)
            page.setVisibility(View.GONE)
        } else if( position == 0.0F ) {       // [0]
            page.setAlpha(1.0F)
            page.setVisibility(View.VISIBLE)
        } else {
            // Position is between [-1,1]
            page.setAlpha(1.0F - Math.abs(position))
            page.setTranslationX(-position * (page.getWidth() / 2))
            page.setVisibility(View.VISIBLE)
        }
    }
}