package com.example.jetpackcomponentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jetpackcomponentsapp.databinding.MainBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

public class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var binder : MainBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(this@MainActivity).get(MainViewModel::class.java) }
    private val adapter : NasaAdapter by lazy { NasaAdapter() }

    override fun onCreate(savedInstanceState : Bundle?) {
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainActivity)
        super.onCreate(savedInstanceState)
        binder?.swipeRefreshLayout?.setOnRefreshListener(this@MainActivity)
        binder?.getViewModel()?.observeAPOD()?.observe(binder?.getLifecycleOwner()!!, object :
            Observer<List<NasaHolderModel>> {
            override fun onChanged(list : List<NasaHolderModel>?) {
                adapter.setItems( list )
                if (binder?.swipeRefreshLayout?.isRefreshing() == true) binder?.swipeRefreshLayout?.setRefreshing(false)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binder?.recyclerView?.setAdapter(adapter)
        binder?.getViewModel()?.requestAPOD()
    }

    override fun onRefresh() { Coroutines.main(this@MainActivity, { scope : CoroutineScope ->
        scope.launch ( block = {
            binder?.recyclerView?.removeAllViews()
            binder?.getViewModel()?.requestAPOD()
        })
    }) }
}