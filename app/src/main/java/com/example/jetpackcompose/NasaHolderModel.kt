package com.example.jetpackcompose

import androidx.compose.runtime.Immutable

@Immutable
public data class NasaHolderModel (
    //val id : Int,
    val title : String,
    val copyright : String,
    val date : String, // Unique identifier: APOD has exactly one picture per day (used as the LazyColumn key)
    val explanation : String,
    val image : String
) {
    companion object {
        private val TAG = NasaHolderModel::class.java.getSimpleName()
    }

    constructor(response : NasaResponseModel) : this(title = response.title ?: Constants.BLANK, copyright = response.copyright ?: Constants.BLANK, date = response.date ?: Constants.BLANK, explanation = response.explanation ?: Constants.BLANK, image = response.hdurl ?: Constants.BLANK) {

    }

    override fun toString() : String {
        return "$TAG($date, $title, $copyright, $explanation, $image)" ?: super.toString()
    }
}