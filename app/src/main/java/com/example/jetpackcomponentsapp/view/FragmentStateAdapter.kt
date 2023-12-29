package com.example.jetpackcomponentsapp.view

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

public class FragmentStateAdapter : FragmentStateAdapter {

    companion object {
        private val TAG : String = FragmentStateAdapter::class.java.getSimpleName()
    }

    constructor(fragmentManager : FragmentManager, lifecycle : Lifecycle) : super(fragmentManager, lifecycle) {
        Log.d(TAG, "constructor()")
        list = listOf<FragmentStateModel>()
    }

    constructor(fragmentManager : FragmentManager, lifecycle : Lifecycle, list : List<FragmentStateModel>) : super(fragmentManager, lifecycle) {
        Log.d(TAG, "constructor() list")
        this.list =  list
    }

    private var list : List<FragmentStateModel>? = null

    override fun createFragment(position : Int) : BaseFragment {
        Log.d(TAG, "createFragment($position)")
        return list?.get(position)?.fragment!!
    }

    override fun getItemCount() : Int {
        return list?.size ?: 0
    }

    public fun setFragments(list : List<FragmentStateModel>?) {
        Log.d(TAG, "setFragments")
        this.list = list
    }

    public fun getTitle(position : Int) : String? {
        return list?.get(position)?.title
    }

    public fun getFragment(position : Int) : BaseFragment? {
        return list?.get(position)?.fragment
    }
}