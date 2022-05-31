package com.example.jetpackcomponentsapp.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.view.fragment.AddFragment
import com.example.jetpackcomponentsapp.view.fragment.MainFragment
import com.example.jetpackcomponentsapp.view.fragment.UpdateFragment

class MainActivity : AppCompatActivity() {

    //private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            callMainFragment()
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            //binding.setViewModel(viewModel)
            //binding.setLifecycleOwner(this)
        }
    }

    private fun callMainFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
    }

    fun callAddFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, AddFragment.newInstance())
                .addToBackStack(
                        AddFragment.getTag())
                .commit()
    }

    fun callUpdateFragment() {
        UpdateFragment
                .newInstance()
                .show(
                        supportFragmentManager.beginTransaction(),
                        UpdateFragment.getTag()
                )
    }

    fun showSoftKeyboard(activity: Activity, showKeyboard : Boolean) {
        var view = activity.currentFocus
        when(showKeyboard) {
            true -> {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
            false ->{
                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                //Find the currently focused view, so we can grab the correct window token from it.

                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = View(activity)
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }
}