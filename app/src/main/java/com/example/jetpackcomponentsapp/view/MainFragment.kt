package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.jetpackcomponentsapp.*
import com.example.jetpackcomponentsapp.databinding.RecyclerBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.util.LoadStateEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment, CustomListener {

    companion object {
        private val TAG = MainFragment::class.java.getSimpleName()

        fun newInstance(listener : MainListener) : MainFragment = MainFragment(listener)
    }

    private var binder : RecyclerBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }
    private val adapter : CustomPagingDataAdapter by lazy { CustomPagingDataAdapter(this@MainFragment) }
    private val listener : MainListener?

    constructor() {
        this.listener = null
    }

    constructor(listener : MainListener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        return binder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainFragment)
        binder?.recyclerView?.setAdapter(adapter)
        Coroutines.main(this@MainFragment, { scope : CoroutineScope ->
            scope.launch ( block = {
                binder?.getViewModel()?.observeItems()?.collectLatest ( action = { pagingDatum ->
                    adapter.submitData( getLifecycle(), pagingDatum )
                } )
            } )
            scope.launch( block = {
                /*
                adapter.loadStateFlow.collectLatest { loadState ->
                    Log.d(TAG, "Not Loading ${loadState.refresh !is LoadState.NotLoading}")
                    Log.d(TAG, "Loading ${loadState.refresh is LoadState.Loading}")
                    Log.d(TAG, "Error ${loadState.refresh is LoadState.Error}")
                }
                */
                binder?.getViewModel()?.observeLoadState()?.collectLatest ( action =  { loadState ->
                    Log.d(TAG, "loadState ${loadState}")
                    if (loadState == LoadStateEnum.LOADING) adapter.onRefresh()
                    if (loadState == LoadStateEnum.ERROR) adapter.onRetry()
                } )
            } )
        } )
    }

    override fun onUpdate(item : CustomHolderModel?, position : Int) {
        viewModel.setUpdate(item)
        listener?.launchUpdate()
    }

    override fun onDelete(item : CustomHolderModel?, position : Int) {
        viewModel.setOnLoadingState()
        viewModel.deleteItem(item)
        viewModel.setDidLoadState()
    }
}