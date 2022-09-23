package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.view.fragments.AddFragment
import com.example.jetpackcomponentsapp.view.fragments.MainFragment
import com.example.jetpackcomponentsapp.view.fragments.UpdateFragment
import com.example.jetpackcomponentsapp.view.listeners.MainListener

public class MainActivity : AppCompatActivity(), View.OnClickListener, MainListener {

    companion object {
        private val TAG = MainActivity::class.java.getSimpleName()
    }

    private var binder : MainBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(this@MainActivity).get(MainViewModel::class.java) }

    private val standByDialog by lazy {
        val builder = this.let { AlertDialog.Builder(it) }
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        val message = dialogView.findViewById<TextView>(R.id.loadingText)
        message.setText( getString(R.string.Processing__please_wait_) )
        builder.setView(dialogView)
        builder.setCancelable(false)
        builder.create()
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainActivity)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            launchMain()
        }
        observeLoadState()
        binder?.floatingActionButtonAdd?.setOnClickListener(this@MainActivity)
        binder?.floatingActionButtonDelete?.setOnClickListener(this@MainActivity)
        getOnBackPressedDispatcher().addCallback(this@MainActivity, getHandleOnBackPressed())
    }

    private fun observeLoadState() {
        viewModel.observeLoadState().observe(this, object : Observer<Boolean> {
            override fun onChanged(isLoading : Boolean?) {
                when(isLoading == true) {
                    true -> standByDialog.show()
                    false -> standByDialog.dismiss()
                }
            }
        })
    }

    override fun onClick(view : View?) {
        if (view == binder?.floatingActionButtonAdd) launchAdd()
        else if (view == binder?.floatingActionButtonDelete) {
            viewModel.deleteAll()
            Toast.makeText(this@MainActivity,"deleteAll()", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(containerViewId : Int, fragment: Fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(containerViewId, fragment, fragment::class.java.getSimpleName())
            .commitNow()
    }

    private fun addToBackStackFragment(containerViewId : Int, fragment : Fragment) {
        if (getSupportFragmentManager().findFragmentByTag(fragment::class.java.getSimpleName()) == null)
            getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment, fragment::class.java.getSimpleName())
                .addToBackStack(fragment::class.java.getSimpleName())
                .commit()
    }

    private fun showDialogFragment(fragment : DialogFragment) {
        fragment.show(getSupportFragmentManager().beginTransaction(), fragment.javaClass.getName())
    }

    private fun launchMain() {
        replaceFragment(R.id.frame_layout, MainFragment.newInstance(this@MainActivity))
    }

    private fun launchAdd() {
        addToBackStackFragment(R.id.frame_layout, AddFragment.newInstance())
    }

    override fun launchUpdate() {
        showDialogFragment(UpdateFragment.newInstance())
    }

    private fun getHandleOnBackPressed() : OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { Log.d(TAG,"handleOnBackPressed()")
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish()
                } else getSupportFragmentManager().popBackStackImmediate()
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Use this method getHandleOnBackPressed"), DeprecationLevel.WARNING)
    override fun onBackPressed() { Log.d(TAG,"onBackPressed()")
        if (getOnBackPressedDispatcher().hasEnabledCallbacks())
            super.onBackPressed()
        else if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed()
        else
            getSupportFragmentManager().popBackStack()
    }
}