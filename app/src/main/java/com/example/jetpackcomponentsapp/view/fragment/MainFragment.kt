package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.RecyclerBinder
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.view.MainListener
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment, CustomListeners {

    companion object {
        private val TAG = MainFragment::class.java.getSimpleName()

        fun newInstance(listener : MainListener) : MainFragment = MainFragment(listener)
    }

    private var binder : RecyclerBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }
    private val adapter : CustomAdapter by lazy { CustomAdapter(this@MainFragment) }
    private val listener : MainListener?
    //private lateinit var itemDecorationHelper: BottomOffsetDecorationHelper

    constructor() {
        listener = null
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
        //itemDecorationHelper = BottomOffsetDecorationHelper(requireContext(), R.dimen.extra_scroll)
        //binder?.recyclerView.removeItemDecoration(itemDecorationHelper)
        //binder?.recyclerView.getLayoutManager()?.setAutoMeasureEnabled(false)
        //binder?.recyclerView.scrollToPosition(0)
        //binder?.recyclerView.addItemDecoration(itemDecorationHelper)
        //binder?.recyclerView?.setHasFixedSize(true)
        Coroutines.main(this@MainFragment, { scope : CoroutineScope ->
            /*
            scope.launch ( block = {
                viewModel.getItems().collect(object : FlowCollector<List<CustomModel>> {
                    override suspend fun emit(list : List<CustomModel>) {
                        Log.d(MainFragment.getTag(),"ID ${list.map { it.id }}, Name ${list.map { it.name }}")
                        binding.recyclerView.removeAllViews()
                        adapter.setItems(list)
                    }
                })
            })
            */
            scope.launch ( block = {
                viewModel.observeItems().collectLatest( action = { list ->
                    Log.d(TAG, "ID ${list.map { it.id }}, Name ${list.map { it.name }}")
                    binder?.recyclerView?.removeAllViews()
                    adapter.setItems(list)
                    //adapter.notifyDataSetChanged()
                })
            })
        })
    }

    override fun onUpdate(item : CustomModel?, position : Int) {
        viewModel.setUpdate(item)
        listener?.launchUpdate()
    }

    override fun onDelete(item : CustomModel?, position : Int) {
        viewModel.deleteItem(item)
    }
}