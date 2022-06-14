package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.web.CountryResponseModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.requestCountry().observe(this, object : Observer<List<CountryResponseModel>> {
            override fun onChanged(list : List<CountryResponseModel>) {
                text_view_result.text = list.map {
                    Log.d("MainActivity",it.Name?:"")
                    it.Name
                }.toString()
            }
        })
    }
}