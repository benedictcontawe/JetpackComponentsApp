package com.example.jetpackcompose

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

public class Repository {

    companion object {
        private val TAG : String = Repository::class.java.getSimpleName()
        private var retrofit : Retrofit? = null
    }

    private val nasaAPI : NasaAPI

    constructor() {
        if (retrofit == null) retrofit = provideRetrofit(
            Constants.API_DOMAIN,
            provideGsonBuilder(),
            provideOkHttpClient()
        )
        nasaAPI = createService(NasaAPI::class.java)
    }

    private fun provideRetrofit(url : String, gson : Gson, okHttpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    private fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    private fun provideOkHttpClient() : OkHttpClient {
        return  OkHttpClient.Builder()
            .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            /*.addInterceptor(object  : Interceptor {
                override fun intercept(chain: Interceptor.Chain) : Response {
                    val original : Request = chain.request()
                    val request : Request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", Constants.API_AUTHORIZATION)
                        .method(original.method, original.body)
                        .build()
                    return chain.proceed(request)
                }
            })*/
            .build()
    }

    private fun <S> createService(serviceClass : Class<S>?) : S {
        return retrofit!!.create(serviceClass)
    }

    private fun getPagingConfig() : PagingConfig {
        return PagingConfig(
            pageSize = 10,
            initialLoadSize = 10, //default: page size * 3
            prefetchDistance = 10, //default: page size
            //maxSize = PagedList.Config.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = false //default: true
        )
    }

    public fun getFlowAPOD(request : NasaRequestModel) : Flow<PagingData<NasaResponseModel>> {
        return Pager (
            config = getPagingConfig(),
            pagingSourceFactory = {
                NasaPagingSource(nasaAPI, request)
            }
        ).flow
    }

    public suspend fun getAPOD(request : NasaRequestModel) : List<NasaResponseModel> {
        val response : Response<List<NasaResponseModel>> = nasaAPI.getAstronomyPictureOfTheDay(request.key!!, request.count!!)
        Log.d(TAG,"isSuccessful() ${response.isSuccessful()}")
        Log.d(TAG,"errorBody() ${response.errorBody()}")
        Log.d(TAG,"body() ${response.body()}")
        Log.d(TAG,"code() ${response.code()}")
        Log.d(TAG,"headers() ${response.headers()}")
        Log.d(TAG,"message() ${response.message()}")
        Log.d(TAG,"raw() ${response.raw()}")
        return if (response.isSuccessful() && response.body() != null) response.body()!!
        else if (!response.isSuccessful()) listOf<NasaResponseModel>()
        else emptyList<NasaResponseModel>()
    }
}