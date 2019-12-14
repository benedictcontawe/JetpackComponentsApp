package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    //private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            callMainFragment()

            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            viewModel.setItems()
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
                .addToBackStack("AddFragment").commit()
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