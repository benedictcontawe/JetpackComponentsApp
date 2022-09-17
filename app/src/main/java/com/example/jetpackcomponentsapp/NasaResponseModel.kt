package com.example.jetpackcomponentsapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NasaResponseModel(
    @SerializedName("title")
    @Expose
    var title : String? = null,

    @SerializedName("copyright")
    @Expose
    var copyright : String? = null,

    @SerializedName("date")
    @Expose
    var date : String? = null,

    @SerializedName("hdurl")
    @Expose
    var hdurl : String? = null,

    @SerializedName("explanation")
    @Expose
    var explanation : String? = null,
) {
    companion object {
        private val TAG = NasaResponseModel::class.java.getSimpleName()
    }
    override fun toString() : String {
        return "$TAG($title, $copyright, $date, $hdurl, $explanation)" ?: super.toString()
    }
}
