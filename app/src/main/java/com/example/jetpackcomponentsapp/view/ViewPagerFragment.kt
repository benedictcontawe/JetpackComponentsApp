package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.ViewPagerBinder
import kotlinx.coroutines.CoroutineScope

class ViewPagerFragment : BaseFragment {

    companion object {
        private val TAG : String = ViewPagerFragment::class.java.getSimpleName()

        public fun newInstance() : ViewPagerFragment = ViewPagerFragment()
    }

    private var binder : ViewPagerBinder? = null
    private var indicatorAdapter : IndicatorAdapter? = null
    private var indicatorLinearLayoutManager : LinearLayoutManager? = null


    constructor() {

    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager,container,false)
        binder?.setViewModel(ViewModelProvider(this@ViewPagerFragment).get(MainViewModel::class.java))
        binder?.setLifecycleOwner(getViewLifecycleOwner())
        return binder?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setIndicator()
    }

    private fun setViewPager() {
        //region For View Binding
        //viewBinding.viewPagerTwo.setAdapter(CustomViewPagerAdapter(dataBinding.getViewModel()?.getItems()))
        //endregion
        binder?.viewPagerTwo?.setAdapter(ViewPagerAdapter(binder?.getViewModel()?.getCustomModels()))
        binder?.viewPagerTwo?.setPageTransformer(FadePageTransformer())
        //dataBinding.viewPagerTwo.adapter.registerAdapterDataObserver()
        //binding.viewPagerTwo.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
    }

    private fun setIndicator() {
        indicatorAdapter = IndicatorAdapter(binder?.getViewModel()?.getItemCount())
        indicatorLinearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binder?.indicatorRecyclerView?.setOnTouchListener(this@ViewPagerFragment)
        binder?.indicatorRecyclerView?.setAdapter(indicatorAdapter)
        binder?.indicatorRecyclerView?.setLayoutManager(indicatorLinearLayoutManager)
        binder?.indicatorRecyclerView?.setHasFixedSize(true)
    }

    private fun getOnPageChangeCallback() : ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position : Int) { Log.d(TAG,"onPageSelected($position)")
                super.onPageSelected(position)
                indicatorAdapter?.setSelected(position)
                //binding.indicatorRecyclerView.scrollToPosition(position)
                //binding.indicatorRecyclerView.getLayoutManager()?.scrollToPosition(position)
                indicatorLinearLayoutManager?.scrollToPositionWithOffset(position, getResources().getDimensionPixelSize(R.dimen.indicator_extra_scroll))
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

    override suspend fun onSetObservers(scope : CoroutineScope) {
        /*
        scope.launch { binder?.getViewModel()?.observeViewPagerLogin()?.collectLatest( action = {

        }) }
        */
    }

    override fun onTouchFragment(view: View, event: MotionEvent): Boolean {
        return if (view == binder?.indicatorRecyclerView) true
        else super.onTouchFragment(view, event)
    }

    override fun onResume() {
        super.onResume()
        binder?.viewPagerTwo?.registerOnPageChangeCallback(getOnPageChangeCallback())
    }

    override fun onPause() {
        super.onPause()
        binder?.viewPagerTwo?.unregisterOnPageChangeCallback(getOnPageChangeCallback())
    }

}