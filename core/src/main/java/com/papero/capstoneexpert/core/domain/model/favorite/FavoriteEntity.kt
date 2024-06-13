package com.papero.capstoneexpert.core.domain.model.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteEntity(
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
    val voteCount: Int?
): Parcelable