package com.example.jetpackcomponentsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, ContactListener {

    private val viewModel : MainViewModel by lazy(LazyThreadSafetyMode.NONE, initializer = {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    })

    private val contactAdapter : ContactAdapter by lazy(LazyThreadSafetyMode.NONE, initializer = {
        ContactAdapter(this@MainActivity)
    })

    companion object {
        private var TAG : String = MainActivity::class.java.simpleName

        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floating_action_button_add.setOnClickListener(this@MainActivity)
        setRecyclerView()
        checkManifestPermission()
        viewModel.observeLiveStandBy().observe(this, object : Observer<Boolean> {
            override fun onChanged(isLoading : Boolean) {
                if (isLoading) {
                    progress_bar.setVisibility(View.VISIBLE)
                } else {
                    progress_bar.setVisibility(View.GONE)
                }
            }
        })
        viewModel.observeLiveContact().observe(this, object : Observer<List<ContactViewHolderModel>>{
            override fun onChanged(list : List<ContactViewHolderModel>) {
                //recycler_view.invalidate()
                recycler_view.removeAllViews()
                contactAdapter.setItems(list)
            }
        })
    }

    private fun setRecyclerView() {
        //recycler_view.setLayoutManager(CustomLinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false))
        recycler_view.setAdapter(contactAdapter)
        recycler_view.setHasFixedSize(true)
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
        recycler_view.addOnScrollListener(setScrollListener())
        ManifestPermission.checkSelfPermission(this@MainActivity, ManifestPermission.contactPermission,
                isGranted = {
                    viewModel.syncContacts()
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
        recycler_view.removeOnScrollListener(setScrollListener())
    }

    override fun onClick(view : View) {
        when(view) {
            floating_action_button_add -> {
                if (progress_bar.getVisibility() == View.GONE) {
                    startActivity(viewModel.addContact())
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
                        floating_action_button_add.show() //floating_action_button_add.setVisibility(View.VISIBLE)
                    }
                    recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && newState == RecyclerView.SCROLL_STATE_DRAGGING -> {
                        Log.d(TAG,"Recycler View Scroll State DRAGGING")
                        floating_action_button_add.hide() //floating_action_button_add.setVisibility(View.GONE)
                    }
                    recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING && newState == RecyclerView.SCROLL_STATE_SETTLING -> {
                        Log.d(TAG,"Recycler View Scroll State SETTLING")
                    }
                }
            }
        }
    }

    override fun onClickItemEdit(item : ContactViewHolderModel, position : Int) {
        if (progress_bar.getVisibility() == View.GONE) {
            startActivity(viewModel.updateContact(item))
        }
    }

    override fun onClickItemDelete(item : ContactViewHolderModel, position : Int) {
        if (progress_bar.getVisibility() == View.GONE) {
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