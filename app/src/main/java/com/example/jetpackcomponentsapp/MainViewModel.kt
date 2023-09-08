package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.view.ButtonFragment
import com.example.jetpackcomponentsapp.view.FragmentStateModel
import com.example.jetpackcomponentsapp.view.TitleFragment

class MainViewModel : AndroidViewModel {

    companion object {
        private lateinit var customRepository : CustomRepository
    }

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
    }

    public fun getItemCount() : Int {
        return customRepository.getItems().size
    }

    public fun getCustomModels() : List<CustomModel> {
        return customRepository.getItems()
    }

    public fun getFragmentStateModels() : List<FragmentStateModel> {
        return listOf<FragmentStateModel> (
            FragmentStateModel( "One" , TitleFragment.newInstance("One")),
            FragmentStateModel( "Two" , ButtonFragment.newInstance("Two")),
            FragmentStateModel( "Three" , TitleFragment.newInstance("Three")),
            FragmentStateModel( "Three" , ButtonFragment.newInstance("Four"))
        )
    }
}