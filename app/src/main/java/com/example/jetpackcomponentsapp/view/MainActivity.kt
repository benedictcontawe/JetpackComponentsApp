package com.example.jetpackcomponentsapp.view

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.MainBinder
import kotlinx.coroutines.CoroutineScope

public class MainActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        //private lateinit var viewBinding : ActivityMainBinding
        fun newIntent(context : Context) : Intent = Intent(context.applicationContext, MainActivity::class.java)
    }

    private var binder : MainBinder? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        //setContentView(R.layout.activity_main)
        //region For View Binding
        //viewBinding = ActivityMainBinding.inflate(getLayoutInflater())
        //setContentView(viewBinding.getRoot())
        //endregion
        binder = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        if (savedInstanceState == null) {
            binder?.setViewModel(ViewModelProvider(this@MainActivity).get(MainViewModel::class.java))
            binder?.setLifecycleOwner(this@MainActivity)
            popAllBackStack()
        }
    }

    override suspend fun onSetObservers(scope : CoroutineScope) {

    }

    override fun onConfigurationChanged(newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "onConfigurationChanged ORIENTATION_PORTRAIT")
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "onConfigurationChanged ORIENTATION_LANDSCAPE")
        }
    }

    override fun onClick(view : View?) {
        if (view == binder?.viewPagerButton) replaceFragment(R.id.frame_layout, ViewPagerFragment.newInstance(), R.anim.fade_in, R.anim.fade_out)
        else if(view == binder?.fragmentstateButton) replaceFragment(R.id.frame_layout, FragmentStateFragment.newInstance(), R.anim.fade_in, R.anim.fade_out)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        binder?.viewPagerButton?.setOnClickListener(this@MainActivity)
        binder?.fragmentstateButton?.setOnClickListener(this@MainActivity)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        binder?.viewPagerButton?.setOnClickListener(null)
        binder?.fragmentstateButton?.setOnClickListener(null)
    }
}