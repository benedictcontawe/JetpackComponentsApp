package com.example.jetpackcomponentsapp.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.view.fragment.AddFragment
import com.example.jetpackcomponentsapp.view.fragment.MainFragment
import com.example.jetpackcomponentsapp.view.fragment.UpdateFragment

public class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
    }

    //private lateinit var binding : MainBinder
    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            callMainFragment()
            //binding.setViewModel(viewModel)
            //binding.setLifecycleOwner(this)
        }
    }

    private fun callMainFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    public fun callAddFragment() {
        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, AddFragment.newInstance())
            .addToBackStack( AddFragment.TAG )
            .commit()
    }

    public fun callUpdateFragment(model : CustomModel) {
        UpdateFragment
            .newInstance(model)
            .show(
                getSupportFragmentManager().beginTransaction(),
                UpdateFragment.TAG
            )
    }

    public fun showSoftKeyboard(activity : Activity, showKeyboard : Boolean) {
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