package com.vantrai.challengew6.activity

import com.vantrai.challengew6.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRateMovie(
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("page") page: Int
    ): Call<MovieResponse>
}