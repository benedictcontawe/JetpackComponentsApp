package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.FragmentStateBinder
import kotlinx.coroutines.CoroutineScope

class FragmentStateFragment : BaseFragment {

    companion object {
        private val TAG : String = FragmentStateFragment::class.java.getSimpleName()

        public fun newInstance() : FragmentStateFragment = FragmentStateFragment()
    }

    private var binder : FragmentStateBinder? = null
    private var adapter : FragmentStateAdapter? = null

    constructor() {

    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_state_fragment,container,false)
        binder?.setViewModel(ViewModelProvider(this@FragmentStateFragment).get(MainViewModel::class.java))
        binder?.setLifecycleOwner(getViewLifecycleOwner())
        return binder?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        adapter = FragmentStateAdapter(requireActivity().getSupportFragmentManager(), getLifecycle())
        adapter?.setFragments(binder?.getViewModel()?.getFragmentStateModels())
        binder?.viewPagerTwo?.setAdapter(adapter)
        binder?.viewPagerTwo?.setPageTransformer(FadePageTransformer())
    }

    override suspend fun onSetObservers(scope : CoroutineScope) {

    }
}