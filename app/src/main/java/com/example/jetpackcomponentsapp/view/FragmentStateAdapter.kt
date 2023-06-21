package com.example.jetpackcomponentsapp.view

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentStateAdapter : FragmentStateAdapter {

    constructor(fragmentManager : FragmentManager, lifecycle : Lifecycle) : super(fragmentManager, lifecycle) {
        list = listOf<FragmentStateModel>()
    }

    constructor(fragmentManager : FragmentManager, lifecycle : Lifecycle, list : List<FragmentStateModel>) : super(fragmentManager, lifecycle) {
        this.list =  list
    }

    private var list : List<FragmentStateModel>? = null

    override fun createFragment(position : Int) : BaseFragment {
        return list?.get(position)?.fragment!!
    }

    override fun getItemCount() : Int {
        return list?.size ?: 0
    }

    public fun setFragments(list : List<FragmentStateModel>?) {
        this.list = list
    }

    public fun getTitle(position : Int) : String? {
        return list?.get(position)?.title
    }

    public fun getFragment(position : Int) : BaseFragment? {
        return list?.get(position)?.fragment
    }
}