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
        private lateinit var dataBinding : MainBinder
        //private lateinit var viewBinding : ActivityMainBinding
        private lateinit var indicatorAdapter : IndicatorAdapter
        private lateinit var indicatorLinearLayoutManager : LinearLayoutManager
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //region For View Binding
        //viewBinding = ActivityMainBinding.inflate(getLayoutInflater())
        //setContentView(viewBinding.getRoot())
        //endregion
        dataBinding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        if (savedInstanceState == null) {
            dataBinding.setViewModel(ViewModelProvider(this@MainActivity).get(MainViewModel::class.java))
            dataBinding.setLifecycleOwner(this@MainActivity)
            setViewPager()
            setIndicator()
        }
    }

    private fun setViewPager() {
        //region For View Binding
        //viewBinding.viewPagerTwo.setAdapter(CustomViewPagerAdapter(dataBinding.getViewModel()?.getItems()))
        //endregion
        dataBinding.viewPagerTwo.setAdapter(CustomViewPagerAdapter(dataBinding.getViewModel()?.getItems()))
        dataBinding.viewPagerTwo.setPageTransformer(FadePageTransformer())
        //dataBinding.viewPagerTwo.adapter.registerAdapterDataObserver()
        //binding.viewPagerTwo.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
    }

    private fun setIndicator() {
        indicatorAdapter = IndicatorAdapter(dataBinding.getViewModel()?.getItemCount())
        indicatorLinearLayoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
        dataBinding.indicatorRecyclerView.setOnTouchListener(this@MainActivity)
        dataBinding.indicatorRecyclerView.setAdapter(indicatorAdapter)
        dataBinding.indicatorRecyclerView.setLayoutManager(indicatorLinearLayoutManager)
        dataBinding.indicatorRecyclerView.setHasFixedSize(true)
    }

    private fun getOnPageChangeCallback() : ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position : Int) { Log.d(TAG,"onPageSelected($position)")
                super.onPageSelected(position)
                indicatorAdapter.setSelected(position)
                //binding.indicatorRecyclerView.scrollToPosition(position)
                //binding.indicatorRecyclerView.getLayoutManager()?.scrollToPosition(position)
                indicatorLinearLayoutManager.scrollToPositionWithOffset(position, getResources().getDimensionPixelSize(R.dimen.indicator_extra_scroll))
            }

            override fun onPageScrollStateChanged(state : Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2.SCROLL_STATE_IDLE")
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
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
        return if (view == dataBinding.indicatorRecyclerView) true
        else false
    }

    override fun onResume() {
        super.onResume()
        dataBinding.viewPagerTwo.registerOnPageChangeCallback(getOnPageChangeCallback())
    }

    override fun onPause() {
        super.onPause()
        dataBinding.viewPagerTwo.unregisterOnPageChangeCallback(getOnPageChangeCallback())
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