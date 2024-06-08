package com.papero.capstoneexpert.core.data.source.remote

import com.google.gson.annotations.SerializedName
import com.papero.capstoneexpert.core.data.source.remote.now_playing.DateRangeResponse

data class BaseDataSourceMovieResponse<T>(
    @field:SerializedName("dates")
    val dates: DateRangeResponse?,

    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("results")
    val results: T,

    @field:SerializedName("total_pages")
    val totalPages: Int?,

    @field:SerializedName("total_results")
    val totalResults: Int?
)
