package com.example.jetpackcomponentsapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val list : MutableList<CustomModel> = mutableListOf<CustomModel>()


    constructor() : super() {
        list.addAll(getCustomModels())
    }

    private fun getCustomModels() : List<CustomModel> {
        return listOf<CustomModel> (
            CustomModel(0, "0"),
            CustomModel(1, "1"),
            CustomModel(2, "2", R.drawable.ic_android_black),
            CustomModel(3, "3"),
            CustomModel(4, "4", R.drawable.ic_android_black),
            CustomModel(5, "5"),
            CustomModel(6, "6"),
            CustomModel(7, "7", R.drawable.ic_android_black),
            CustomModel(8, "8"),
            CustomModel(9, "9"),
            CustomModel(10, "10", R.drawable.ic_android_black),
            CustomModel(11, "11"),
            CustomModel(12, "12", R.drawable.ic_android_black),
            CustomModel(13, "13"),
        )
    }

    public fun getListCount() : Int {
        return list.size
    }

    public fun isIconNil(page : Int) : Boolean {
        return list.get(page).icon == null
    }

    public fun getIcon(page : Int) : Int? {
        return list.get(page).icon
    }

    public fun getName(page : Int) : String {
        return list.get(page).name
    }

    override fun onCleared() {

        super.onCleared()
    }
}