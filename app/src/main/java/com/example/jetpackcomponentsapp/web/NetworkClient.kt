package com.example.jetpackcomponentsapp.web

import com.example.jetpackcomponentsapp.repository.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class NetworkClient {
    companion object{
        private const val BASE_URL = "https://restcountries.eu"
        private var retrofit : Retrofit? = null
        /*
        This public static method will return Retrofit client
        anywhere in the appplication
        */
        private fun provideRetrofit() : Retrofit? {
            //If condition to ensure we don't create multiple retrofit instances in a single application
            if (retrofit == null) {
                //Defining the Retrofit using Builder
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                        .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                        .client(provideOkHttpClient())
                        .build()
            }
            return retrofit
        }

        private fun provideOkHttpClient() : OkHttpClient {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            /*
            okHttpClient.addInterceptor(object  : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original : Request = chain.request()
                    val request : Request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", Constants.SERVICE_BASE_AUTHORIZATION)
                        .method(original.method, original.body)
                        .build()
                    return chain.proceed(request)
                }
            })
            */
            return okHttpClient.build()
        }

        public fun <S> createService(serviceClass: Class<S>?) : S {
            provideRetrofit()  //Obtain an instance of Retrofit by calling the static method.
            return retrofit!!.create(serviceClass)
        }
    }
}