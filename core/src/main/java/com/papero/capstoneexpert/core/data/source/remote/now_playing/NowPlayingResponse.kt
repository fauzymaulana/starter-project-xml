package com.papero.capstoneexpert.core.data.source.remote.now_playing

import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    @field:SerializedName("adult")
    val adult: Boolean?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("genre_ids")
    val genreIds: MutableList<Int>?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("original_language")
    val originalLanguage: String?,

    @field:SerializedName("original_title")
    val originalTitle: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("popularity")
    val popularity: Float?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Float?,

    @field:SerializedName("vote_count")
    val voteCount: Int?
)