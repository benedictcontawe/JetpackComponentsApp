package com.example.jetpackcomponentsapp

import androidx.lifecycle.ViewModel
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.view.ButtonFragment
import com.example.jetpackcomponentsapp.view.FragmentStateModel
import com.example.jetpackcomponentsapp.view.TitleFragment

class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor() : super() {
        repository = CustomRepository.getInstance()
    }

    private val repository : CustomRepository

    public fun getItemCount() : Int {
        return repository.getItems().size
    }

    public fun getCustomModels() : List<CustomModel> {
        return repository.getItems()
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