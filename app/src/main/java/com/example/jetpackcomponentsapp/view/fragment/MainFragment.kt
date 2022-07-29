package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.view.MainActivity
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment(), OnRefreshListener, CustomListeners {

    companion object {
        fun newInstance() : MainFragment = MainFragment()

        fun getTag() : String {
            return MainFragment::class.java.getSimpleName()
        }
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setRecyclerView()
        setFloatingActionButton()
        binding.swipeRefreshLayout.setOnRefreshListener(this@MainFragment)
        //viewModel.setItems()
    }

    private fun setRecyclerView() {
        adapter = CustomAdapter(requireContext(), this@MainFragment)
        //itemDecorationHelper = BottomOffsetDecorationHelper(context!!,R.dimen.extra_scroll)
        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        //binding.recyclerView.removeItemDecoration(itemDecorationHelper)
        binding.recyclerView.adapter = adapter
        (binding.recyclerView.layoutManager as LinearLayoutManager).setAutoMeasureEnabled(false)
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
                Toast.makeText(requireContext(), "All Deleted Swipe down to Refresh!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onAdd() {
        (activity as MainActivity).callAddFragment()
    }

    override fun onRefresh() { Coroutines.main(this@MainFragment, { scope : CoroutineScope ->
        scope.launch ( block = {
            binding.recyclerView.removeAllViews()
            adapter.setItems(viewModel.getItems())
            delay(1000)
            if (binding.swipeRefreshLayout.isRefreshing()) {
                binding.swipeRefreshLayout.setRefreshing(false)
            }
        })
    }) }

    override fun onUpdate(item : CustomModel, position : Int) {
        viewModel.setUpdate(item)
        (activity as MainActivity).callUpdateFragment()
    }

    override fun onDelete(item : CustomModel, position : Int) {
        viewModel.deleteItem(item)
        Toast.makeText(requireContext(), "Item Deleted Swipe down to Refresh!", Toast.LENGTH_SHORT).show()
    }
}