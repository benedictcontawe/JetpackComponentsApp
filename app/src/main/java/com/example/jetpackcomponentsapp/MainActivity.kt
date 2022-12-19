package com.example.jetpackcomponentsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.databinding.MainBinder

class MainActivity : AppCompatActivity(), View.OnClickListener, ContactListener {

    companion object {
        private var TAG : String = MainActivity::class.java.getSimpleName()

        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    private var binder : MainBinder? = null

    private val viewModel : MainViewModel by lazy(LazyThreadSafetyMode.NONE, initializer = {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    })

    private val contactAdapter : ContactAdapter by lazy(LazyThreadSafetyMode.NONE, initializer = {
        ContactAdapter(this@MainActivity)
    })

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainActivity)
        binder?.floatingActionButtonAdd?.setOnClickListener(this@MainActivity)
        binder?.floatingActionButtonSort?.setOnClickListener(this@MainActivity)
        setRecyclerView()
        checkManifestPermission()
        viewModel.observeLiveStandBy().observe(this, object : Observer<Boolean> {
            override fun onChanged(isLoading : Boolean) {
                if (isLoading) {
                    binder?.progressBar?.setVisibility(View.VISIBLE)
                } else {
                    binder?.progressBar?.setVisibility(View.GONE)
                }
            }
        })
        viewModel.observeLiveContact().observe(this, object : Observer<List<ContactViewHolderModel>>{
            override fun onChanged(list : List<ContactViewHolderModel>) {
                //binder?.recyclerView?.invalidate()
                binder?.recyclerView?.removeAllViews()
                contactAdapter.setItems(list)
            }
        })
    }

    private fun setRecyclerView() {
        //binder?.recyclerView?.setLayoutManager(CustomLinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false))
        binder?.recyclerView?.setAdapter(contactAdapter)
        binder?.recyclerView?.setHasFixedSize(true)
    }

    private fun checkManifestPermission() {
        ManifestPermission.checkSelfPermission(this@MainActivity, ManifestPermission.contactPermission,
                isDenied = {
                    ManifestPermission.requestPermissions(this@MainActivity,
                            ManifestPermission.contactPermission,
                            ManifestPermission.CONTACT_PERMISSION_CODE
                    )
                }
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume()")
        Log.d("MainViewModel", "Available Processors ${Runtime.getRuntime().availableProcessors()}")
        binder?.recyclerView?.addOnScrollListener(setScrollListener())
        ManifestPermission.checkSelfPermission(this@MainActivity, ManifestPermission.contactPermission,
                isGranted = {
                    viewModel.syncAdded()
                    viewModel.syncDeleted()
                    viewModel.syncNames()
                    viewModel.syncPhotos()
                    viewModel.syncNumbers()
                    viewModel.syncEmails()
                    viewModel.sortContacts()
                }
        )
    }

    override fun onPause() {
        super.onPause()
        binder?.recyclerView?.removeOnScrollListener(setScrollListener())
    }

    override fun onClick(view : View) {
        when(view) {
            binder?.floatingActionButtonAdd -> {
                if (binder?.progressBar?.getVisibility() == View.GONE) {
                    startActivity(viewModel.addContact())
                }
            }
            binder?.floatingActionButtonSort -> {
                if (binder?.progressBar?.getVisibility() == View.GONE) {
                    viewModel.sortContacts()
                }
            }
        }
    }

    private fun setScrollListener() : RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
                super.onScrolled(recyclerView, dx, dy)
                val canScrollUp : Boolean = recyclerView.canScrollVertically(-1)
                val canScrollDown : Boolean = recyclerView.canScrollVertically(1)
                when {
                    canScrollUp && canScrollDown -> {
                        Log.d(TAG,"Recycler View at Middle")
                    }
                    canScrollDown && !canScrollUp -> {
                        Log.d(TAG,"Recycler View top reached")
                    }
                    canScrollUp && !canScrollDown -> {
                        Log.d(TAG,"Recycler View bottom reached")
                    }
                }

                when {
                    dy > 0 -> {
                        Log.d(TAG,"Recycler View Scrolling Down")
                    }
                    dy < 0 -> {
                        Log.d(TAG,"Recycler View Scrolling Up")
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView : RecyclerView, newState : Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when {
                    recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && newState == RecyclerView.SCROLL_STATE_IDLE -> {
                        Log.d(TAG,"Recycler View Scroll State IDLE")
                        binder?.floatingActionButtonAdd?.show() //binder?.floatingActionButtonAdd?.setVisibility(View.VISIBLE)
                    }
                    recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && newState == RecyclerView.SCROLL_STATE_DRAGGING -> {
                        Log.d(TAG,"Recycler View Scroll State DRAGGING")
                        binder?.floatingActionButtonAdd?.hide() //binder?.floatingActionButtonAdd?.setVisibility(View.GONE)
                    }
                    recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING && newState == RecyclerView.SCROLL_STATE_SETTLING -> {
                        Log.d(TAG,"Recycler View Scroll State SETTLING")
                    }
                }
            }
        }
    }

    override fun onClickItemEdit(item : ContactViewHolderModel, position : Int) {
        if (binder?.progressBar?.getVisibility() == View.GONE) {
            startActivity(viewModel.updateContact(item))
        }
    }

    override fun onClickItemDelete(item : ContactViewHolderModel, position : Int) {
        if (binder?.progressBar?.getVisibility() == View.GONE) {
            viewModel.deleteContact(item, position)
        }
    }

    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>, grantResults : IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ManifestPermission.checkPermissionsResult(this@MainActivity, permissions, grantResults,
                isNeverAskAgain =  {
                    if (requestCode == ManifestPermission.CONTACT_PERMISSION_CODE) {
                        ManifestPermission.showRationalDialog(this@MainActivity)
                    }
                }, isDenied = {
            if (requestCode == ManifestPermission.CONTACT_PERMISSION_CODE) {
                checkManifestPermission()
            }
        }
        )
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG,"onActivityResult($requestCode,$resultCode,$data)")
        if (requestCode == ManifestPermission.SETTINGS_PERMISSION_CODE)
            Toast.makeText(this,"PERMISSION_SETTINGS_CODE",Toast.LENGTH_SHORT).show()
    }
}