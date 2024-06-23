package com.papero.capstoneexpert.core.domain.model.now_playing

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NowPlayingEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: MutableList<String>?,
    val id: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Float?,
    val voteCount: Int?,
    var isFavorite: Boolean = false
) : Parcelable

