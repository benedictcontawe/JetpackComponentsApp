package com.example.jetpackcomponentsapp

public data class NasaHolderModel (
    val id : Int,
    val title : String,
    val copyright : String,
    val date : String,
    val explanation : String,
    val image : String
) {
    companion object {
        private val TAG = NasaHolderModel::class.java.getSimpleName()
    }
    constructor(id : Int, response : NasaResponseModel) : this(id = id ?: -1, title = response.title ?: Constants.BLANK, copyright = response.copyright ?: Constants.BLANK, date = response.date ?: Constants.BLANK, explanation = response.explanation ?: Constants.BLANK, image = response.hdurl ?: Constants.BLANK) {

    }
    override fun toString() : String {
        return "$TAG($id, $title, $copyright, $explanation, $image)" ?: super.toString()
    }
}