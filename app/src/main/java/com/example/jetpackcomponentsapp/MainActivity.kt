package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.jetpackcomponentsapp.databinding.MainBinder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this) // this is needed for liveData works correctly in XML.

        setEventListeners()
    }

    private fun setEventListeners() {
        /*binding.buttonSendData.setOnClickListener {
            binding.viewModel!!.setCustomModelLiveData(
                    CustomModel(
                            binding.exitTextFirstName.text.toString(),
                            binding.exitTextLastName.text.toString()
                    )
            )
        }*/
        binding.buttonSendData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                binding.viewModel!!.setCustomModelLiveData(
                        CustomModel(
                                binding.exitTextFirstName.text.toString(),
                                binding.exitTextLastName.text.toString()
                        )
                )
            }

        })
    }
}
