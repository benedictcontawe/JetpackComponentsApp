package com.example.jetpackcomponentsapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI { //https://api.nasa.gov?api_key=xxxxx&count=###
    @GET(Constants.API_GET) //@HTTP(method = "GET", path = Constants.API_GET, hasBody = true)
    public suspend fun getAstronomyPictureOfTheDay( //@Body model : NasaRequestModel
        @Query("api_key") key : String,
        @Query("count") count : Int
    ) : Response<List<NasaResponseModel>>
}