package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.databinding.MainBinder

public class MainActivity : AppCompatActivity(), View.OnTouchListener {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        private lateinit var binding : MainBinder
        private lateinit var indicatorAdapter : IndicatorAdapter
        private lateinit var indicatorLinearLayoutManager : LinearLayoutManager
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        if (savedInstanceState == null) {
            binding.setViewModel(ViewModelProvider(this@MainActivity).get(MainViewModel::class.java))
            binding.setLifecycleOwner(this@MainActivity)
            setViewPager()
            setIndicator()
        }
    }

    private fun setViewPager() {
        binding.viewPagerTwo.setAdapter(CustomViewPagerAdapter(binding.getViewModel()?.getItems()))
        //binding.viewPagerTwo.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
    }

    private fun setIndicator() {
        indicatorAdapter = IndicatorAdapter(binding.getViewModel()?.getItemCount())
        indicatorLinearLayoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
        binding.indicatorRecyclerView.setOnTouchListener(this@MainActivity)
        binding.indicatorRecyclerView.setAdapter(indicatorAdapter)
        binding.indicatorRecyclerView.setLayoutManager(indicatorLinearLayoutManager)
        binding.indicatorRecyclerView.setHasFixedSize(true)
    }

    private fun getOnPageChangeCallback() : ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position : Int) { Log.d(TAG,"onPageSelected($position)")
                super.onPageSelected(position)
                indicatorAdapter.setSelected(position)
                //binding.indicatorRecyclerView.scrollToPosition(position)
                //binding.indicatorRecyclerView.getLayoutManager()?.scrollToPosition(position)
                indicatorLinearLayoutManager.scrollToPositionWithOffset(position,resources.getDimensionPixelSize(R.dimen.extra_scroll))
            }

            override fun onPageScrollStateChanged(state : Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2.SCROLL_STATE_IDLE")
                    }
                    ViewPager2 . SCROLL_STATE_DRAGGING -> {
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2 . SCROLL_STATE_DRAGGING")
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2.SCROLL_STATE_SETTLING")
                    }
                }
            }

            override fun onPageScrolled(position : Int, positionOffset : Float, positionOffsetPixels : Int) { //Log.d(TAG,"onPageScrolled($position,$positionOffset, $positionOffsetPixels)")
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }
    }

    override fun onTouch(view : View?, event : MotionEvent?) : Boolean {
        return if (view == binding.indicatorRecyclerView) true
        else false
    }

    override fun onResume() {
        super.onResume()
        binding.viewPagerTwo.registerOnPageChangeCallback(getOnPageChangeCallback())
    }

    override fun onPause() {
        super.onPause()
        binding.viewPagerTwo.unregisterOnPageChangeCallback(getOnPageChangeCallback())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
        else {
            supportFragmentManager.popBackStack()
        }
        */
    }
}