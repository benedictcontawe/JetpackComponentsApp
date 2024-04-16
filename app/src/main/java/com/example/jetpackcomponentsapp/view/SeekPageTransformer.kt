package com.example.jetpackcomponentsapp.view

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class SeekPageTransformer : ViewPager2.PageTransformer {

    private val pageOffset : Float
    private val pageMargin : Float
    /**
     * @param offscreenPageLimit should be equal to setOffscreenPageLimit method of view pager 2
     */
    constructor(pageOffset : Float, pageMargin : Float) {
        this.pageOffset = pageOffset
        this.pageMargin = pageMargin
    }
    override fun transformPage(page : View, position : Float) {
        val myOffset: Float = position * -(2 * pageOffset + pageMargin)
        if (position < -1) {
            page.translationX = -myOffset
        } else if (position <= 1) {
            val scaleFactor : Float = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
            page.translationX = myOffset
            page.scaleY = scaleFactor
            page.setAlpha(scaleFactor)
        } else {
            page.setAlpha(0f)
            page.translationX = myOffset
        }
    }
}