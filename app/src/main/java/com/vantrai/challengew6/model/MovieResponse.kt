package com.vantrai.challengew6.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val pageIndex: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: ArrayList<Movie>
) {
}