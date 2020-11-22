package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.databinding.MainBinder
import com.example.jetpackcomponentsapp.model.CustomModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TAG : String = MainActivity::class.java.simpleName
        private lateinit var binding : MainBinder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        if (savedInstanceState == null) {
            binding.setViewModel(ViewModelProvider(this@MainActivity).get(MainViewModel::class.java))
            binding.setLifecycleOwner(this@MainActivity)
            observeBoolean()
            observeString()
            observeInt()
            observeDouble()
            observeLong()
            observeCustomModel()
        }
        binding.buttonBoolean.setOnClickListener(this@MainActivity)
        binding.buttonString.setOnClickListener(this@MainActivity)
        binding.buttonInteger.setOnClickListener(this@MainActivity)
        binding.buttonDouble.setOnClickListener(this@MainActivity)
        binding.buttonLong.setOnClickListener(this@MainActivity)
    }
    //region Observer Methods
    private fun observeBoolean() {
        binding.getViewModel()?.observeBoolean()?.observe(this, object : Observer<Boolean> {
                override fun onChanged(value : Boolean?) {
                    Log.d(TAG,"observeBoolean() $value")
                    binding.labelBoolean.setText(value.toString())
                }
        })
    }

    private fun observeString() {
        binding.getViewModel()?.observeString()?.observe(this, object : Observer<String> {
                override fun onChanged(value : String?) {
                    Log.d(TAG,"observeString() $value")
                    binding.labelString.setText(value.toString())
                }
        })
    }

    private fun observeInt() {
        binding.getViewModel()?.observeInt()?.observe(this, object : Observer<Int> {
                override fun onChanged(value : Int?) {
                    Log.d(TAG,"observeInt() $value")
                    binding.labelInteger.setText(value.toString())
                }
        })
    }

    private fun observeDouble() {
        binding.getViewModel()?.observeDouble()?.observe(this, object : Observer<Double> {
                override fun onChanged(value : Double?) {
                    Log.d(TAG,"observeInt() $value")
                    binding.labelDouble.setText(value.toString())
                }
        })
    }

    private fun observeLong() {
        binding.getViewModel()?.observeLong()?.observe(this, object : Observer<Long> {
                override fun onChanged(value : Long?) {
                    Log.d(TAG,"observeInt() $value")
                    binding.labelLong.setText(value.toString())
                }
        })
    }

    private fun observeCustomModel() {
        binding.getViewModel()?.observeCustomModel()?.observe(this, object : Observer<List<CustomModel>> {
            override fun onChanged(value : List<CustomModel>?) {
                Log.d(TAG,"observeCustomModel() $value")
            }
        })
    }
    //endregion
    override fun onClick(view : View) {
        when(view) {
            binding.buttonBoolean -> {
                binding.getViewModel()?.update(
                        binding.checkBoxBoolean.isChecked()
                )
            }
            binding.buttonString -> {
                binding.getViewModel()?.update(
                        binding.editTextString.getText().toString()
                )
            }
            binding.buttonInteger -> {
                binding.getViewModel()?.update(
                        binding.editTextInteger.getText().toString().toInt()
                )
            }
            binding.buttonDouble -> {
                //Toast.makeText(this,"Double Not Supported!",Toast.LENGTH_SHORT).show()
                binding.getViewModel()?.update(
                        binding.editTextDouble.getText().toString().toDouble()
                )
            }
            binding.buttonLong -> {
                binding.getViewModel()?.update(
                        binding.editTextLong.getText().toString().toLong()
                )
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