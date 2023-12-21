package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.view.fragment.AddFragment
import com.example.jetpackcomponentsapp.view.fragment.MainFragment
import com.example.jetpackcomponentsapp.view.fragment.UpdateFragment
import com.example.jetpackcomponentsapp.view.listeners.MainListener

public class MainActivity : AppCompatActivity(), View.OnClickListener, MainListener {

    companion object {
        private val TAG = MainActivity::class.java.getSimpleName()
    }

    private var binder : MainBinder? = null
    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@MainActivity)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            launchMain()
        }
        binder?.floatingActionButtonAdd?.setOnClickListener(this@MainActivity)
        binder?.floatingActionButtonDelete?.setOnClickListener(this@MainActivity)
        onBackPressedDispatcher.addCallback(this@MainActivity, getHandleOnBackPressed())
    }

    override fun onClick(view : View?) {
        if (view == binder?.floatingActionButtonAdd) launchAdd()
        else if (view == binder?.floatingActionButtonDelete) {
            binder?.getViewModel()?.setOnLoadingState()
            viewModel.deleteAll()
            Toast.makeText(this@MainActivity,"deleteAll()", Toast.LENGTH_SHORT).show()
            viewModel.setDidLoadState()
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
        if (onBackPressedDispatcher.hasEnabledCallbacks())
            super.onBackPressed()
        else if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed()
        else
            getSupportFragmentManager().popBackStack()
    }
}