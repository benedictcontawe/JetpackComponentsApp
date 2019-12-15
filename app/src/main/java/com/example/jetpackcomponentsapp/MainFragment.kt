package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.databinding.MainBinder

class MainFragment : Fragment(), CustomListeners {

    companion object {
        fun newInstance() : MainFragment = MainFragment()
    }

    private lateinit var binding: MainBinder
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : CustomAdapter
    //private lateinit var itemDecorationHelper: BottomOffsetDecorationHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setRecyclerView()
        setFloatingActionButton()
    }

    private fun setRecyclerView() {
        adapter = CustomAdapter(context!!,this)
        //itemDecorationHelper = BottomOffsetDecorationHelper(context!!,R.dimen.extra_scroll)

        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        //binding.recyclerView.removeItemDecoration(itemDecorationHelper)
        binding.recyclerView.adapter = adapter

        viewModel.getItems().observe(viewLifecycleOwner, object : Observer<MutableList<CustomModel>> {
            override fun onChanged(list : MutableList<CustomModel>) {
                adapter.setItems(list)
            }
        })
        //binding.recyclerView.scrollToPosition(0)
        //binding.recyclerView.addItemDecoration(itemDecorationHelper)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setFloatingActionButton() {
        binding.floatingActionButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onAdd()
            }
        })
    }

    private fun onAdd() {
        (activity as MainActivity).callAddFragment()
    }

    override fun onUpdate(item: CustomModel, position: Int) {
        Toast.makeText(context,"onUpdate(${item.name},${position})",Toast.LENGTH_SHORT).show()
    }

    override fun onDelete(item: CustomModel, position: Int) {
        Toast.makeText(context,"onDelete(${item.name},${position})",Toast.LENGTH_SHORT).show()
    }
}