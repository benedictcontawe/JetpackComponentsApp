package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.view.MainActivity
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter

class MainFragment : Fragment(), CustomListeners {

    companion object {
        private val TAG : String = MainFragment::class.java.getSimpleName()

        fun newInstance() : MainFragment = MainFragment()
    }

    private lateinit var binding: MainBinder
    private val viewModel : MainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter : CustomAdapter
    //private lateinit var itemDecorationHelper: BottomOffsetDecorationHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(viewLifecycleOwner)

        setRecyclerView()
        setFloatingActionButton()
        //viewModel.setDummyItems()
    }

    private fun setRecyclerView() {
        adapter = CustomAdapter(this@MainFragment)
        //itemDecorationHelper = BottomOffsetDecorationHelper(context!!,R.dimen.extra_scroll)

        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

        //binding.recyclerView.removeItemDecoration(itemDecorationHelper)
        binding.recyclerView.adapter = adapter

        (binding.recyclerView.layoutManager as LinearLayoutManager).setAutoMeasureEnabled(false)
        viewModel.getItems().observe(viewLifecycleOwner, object : Observer<List<CustomModel>> {
            override fun onChanged(list : List<CustomModel>) {
                Log.d("MainFragment","ID ${list.map { it.id }}, Name ${list.map { it.name }}")
                //binding.recyclerView.removeAllViews()
                adapter.setItems(list)
            }
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
        (requireActivity() as MainActivity).callAddFragment()
    }

    override fun onUpdate(model : CustomModel, position : Int) {
        (requireActivity() as MainActivity).callUpdateFragment(model)
    }

    override fun onDelete(model : CustomModel, position : Int) {
        viewModel.deleteItem(model)
    }
}