package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.RecyclerBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.util.LoadStateEnum
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter
import com.example.jetpackcomponentsapp.view.adapter.CustomPagingDataAdapter
import com.example.jetpackcomponentsapp.view.fragments.BaseFragment
import com.example.jetpackcomponentsapp.view.listeners.CustomListeners
import com.example.jetpackcomponentsapp.view.listeners.MainListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment, CustomListeners {

    companion object {
        private val TAG : String = MainFragment::class.java.getSimpleName()

        public fun newInstance(listener : MainListener) : MainFragment = MainFragment(listener)
    }

    private var binder : RecyclerBinder? = null
    private val viewModel : MainViewModel by activityViewModels<MainViewModel>()
    private val adapter : CustomPagingDataAdapter by lazy { CustomPagingDataAdapter(this@MainFragment) }
    private val listener : MainListener?
    //private val itemDecorationHelper: BottomOffsetDecorationHelper by lazy { BottomOffsetDecorationHelper(requireContext(), R.dimen.extra_scroll) }

    constructor() {
        listener = null
    }

    constructor(listener : MainListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        //requireActivity().onBackPressedDispatcher.addCallback(this, getHandleOnBackPressed())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        return binder?.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainFragment)
        binder?.recyclerView?.setAdapter(adapter)
        //binder?.recyclerView.removeItemDecoration(itemDecorationHelper)
        //binder?.recyclerView.getLayoutManager()?.setAutoMeasureEnabled(false)
        //binder?.recyclerView.scrollToPosition(0)
        //binder?.recyclerView.addItemDecoration(itemDecorationHelper)
        //binder?.recyclerView?.setHasFixedSize(true)
        Coroutines.main(this@MainFragment, { scope : CoroutineScope ->
            scope.launch ( block = {
                binder?.getViewModel()?.observeItems()?.collectLatest ( action = { pagingDatum ->
                    adapter.submitData( lifecycle, pagingDatum )
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
        })
    }

    override fun onUpdate(model : CustomHolderModel?, position : Int) {
        viewModel.setUpdate(model)
        listener?.launchUpdate()
    }

    override fun onDelete(model : CustomHolderModel?, position : Int) {
        binder?.getViewModel()?.setOnLoadingState()
        viewModel.deleteItem(model)
        binder?.getViewModel()?.setDidLoadState()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}