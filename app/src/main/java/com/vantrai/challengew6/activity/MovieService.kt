package com.vantrai.challengew6.activity

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieService {
    private var api: MovieApi

    init {
        api = createInstance()
    }

    companion object {
        private var mInstance: MovieService? = null

        fun getInstance() = mInstance ?: synchronized(this) {
            mInstance ?: MovieService().also { mInstance = it }
        }
    }

    private fun createInstance(): MovieApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(MovieApi::class.java)
    }

    fun getApi(): MovieApi = api
}