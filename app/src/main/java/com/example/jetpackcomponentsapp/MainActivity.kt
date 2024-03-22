package com.example.jetpackcomponentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jetpackcomponentsapp.databinding.MainBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

public class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private val TAG = MainActivity::class.java.getSimpleName()
    }

    private var binder : MainBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(this@MainActivity).get(MainViewModel::class.java) }
    private val adapter : NasaPagingDataAdapter by lazy { NasaPagingDataAdapter() }

    override fun onCreate(savedInstanceState : Bundle?) {
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainActivity)
        super.onCreate(savedInstanceState)
        binder?.swipeRefreshLayout?.setOnRefreshListener(this@MainActivity)
        binder?.recyclerView?.setAdapter(adapter)//binder?.getViewModel()?.requestAPOD()
        Coroutines.main(this@MainActivity, { scope: CoroutineScope ->
            scope.launch(block = {
                binder?.getViewModel()?.observeAPOD()?.collectLatest(action = { pagingDatum ->
                    adapter.submitData (
                        lifecycle = lifecycle,
                        pagingDatum
                    )
                })
                /*
                binder?.getViewModel()?.observeLiveAPOD()?.observe(this@MainActivity, object : Observer<PagingData<NasaHolderModel>> {
                    override fun onChanged(pagingDatum : PagingData<NasaHolderModel>) {
                        adapter.submitData (
                            lifecycle = lifecycle,
                            pagingDatum
                        )
                    }
                })
                */
            })
        } )
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binder?.swipeRefreshLayout?.setRefreshing(loadState.refresh is LoadState.Loading)
                binder?.appendLoading?.setVisibility (
                    if (loadState.append is LoadState.Loading) View.VISIBLE
                    else View.GONE
                )
            } else {
                binder?.swipeRefreshLayout?.setRefreshing(false)
                binder?.appendLoading?.setVisibility(View.GONE)
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onRefresh() { Coroutines.main(this@MainActivity, { scope : CoroutineScope ->
        scope.launch ( block = {
            //binder?.recyclerView?.removeAllViews()
            adapter.refresh()
            //binder?.getViewModel()?.requestAPOD()
        })
    }) }
}