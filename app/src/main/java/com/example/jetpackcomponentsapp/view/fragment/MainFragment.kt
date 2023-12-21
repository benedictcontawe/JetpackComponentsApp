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
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter
import com.example.jetpackcomponentsapp.view.fragments.BaseFragment
import com.example.jetpackcomponentsapp.view.listeners.CustomListeners
import com.example.jetpackcomponentsapp.view.listeners.MainListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment, CustomListeners {

    companion object {
        private val TAG : String = MainFragment::class.java.getSimpleName()

        public fun newInstance(listener : MainListener) : MainFragment = MainFragment(listener)
    }

    private var binder : RecyclerBinder? = null
    private val viewModel : MainViewModel by activityViewModels<MainViewModel>()
    private val adapter : CustomAdapter by lazy { CustomAdapter(this@MainFragment) }
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
            /*
            scope.launch ( block = {
                viewModel.observeItems().collect(object : FlowCollector<List<CustomModel>> {
                    override suspend fun emit(value : List<CustomModel>) {
                        Log.d(MainFragment.TAG,"ID ${value.map { it.id }}, Name ${value.map { it.name }}")
                        //binder?.recyclerView?.removeAllViews()
                        adapter.setItems(value)
                    }
                })
            })
            */
            scope.launch ( block = {
                viewModel.observeItems().collectLatest( action = { list ->
                    Log.d(TAG, "ID ${list.map { it.id }}, Name ${list.map { it.name }}")
                    //binder?.recyclerView?.removeAllViews()
                    adapter.setItems(list)
                    //adapter.notifyDataSetChanged()
                })
            })
        })
    }

    override fun onUpdate(model : CustomModel?, position : Int) {
        viewModel.setUpdate(model)
        listener?.launchUpdate()
    }

    override fun onDelete(model : CustomModel?, position : Int) {
        viewModel.deleteItem(model)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}