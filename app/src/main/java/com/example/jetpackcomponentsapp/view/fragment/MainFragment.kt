package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.view.MainActivity
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment(), CustomListeners {

    companion object {
        fun newInstance() : MainFragment = MainFragment()

        private val TAG = MainFragment::class.java.getSimpleName()
    }

    private lateinit var binding: MainBinder
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : CustomAdapter
    //private lateinit var itemDecorationHelper: BottomOffsetDecorationHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(getViewLifecycleOwner())
        setRecyclerView()
        setFloatingActionButton()
        //viewModel.setItems()
    }

    private fun setRecyclerView() {
        adapter = CustomAdapter(this@MainFragment)
        //itemDecorationHelper = BottomOffsetDecorationHelper(context!!,R.dimen.extra_scroll)
        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        //binding.recyclerView.removeItemDecoration(itemDecorationHelper)
        binding.recyclerView.adapter = adapter
        (binding.recyclerView.layoutManager as LinearLayoutManager).setAutoMeasureEnabled(false)
        Coroutines.main(this@MainFragment, { scope : CoroutineScope ->
            scope.launch ( block = {
                viewModel.observeItems().collect(object : FlowCollector<List<CustomModel>> {
                    override suspend fun emit(value : List<CustomModel>) {
                        Log.d(TAG,"observeItems ID ${value.map { it.id }}, Name ${value.map { it.name }}")
                        binding.recyclerView.removeAllViews()
                        adapter.setItems(value)
                        viewModel.getFirst(value.firstOrNull())
                    }
                })
            })
            scope.launch ( block = {
                viewModel.observeItem().collectLatest( action = { item ->
                    Log.d(TAG,"observeItem ID ${item.id}, Name ${item.name}")
                })
            })
        })
        //binding.recyclerView.scrollToPosition(0)
        //binding.recyclerView.addItemDecoration(itemDecorationHelper)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setFloatingActionButton() {
        binding.floatingActionButtonAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                onAdd()
            }
        })
        binding.floatingActionButtonDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                viewModel.deleteAll()
                Toast.makeText(context,"deleteAll()",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onAdd() {
        (activity as MainActivity).callAddFragment()
        //viewModel.setItems()
    }

    override fun onUpdate(item : CustomModel, position : Int) {
        viewModel.setUpdate(item)
        (activity as MainActivity).callUpdateFragment()
    }

    override fun onDelete(item : CustomModel, position : Int) {
        viewModel.deleteItem(item)
    }
}