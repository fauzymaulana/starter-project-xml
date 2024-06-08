package com.papero.capstoneexpert.core.domain.model.genre

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreEntity(
    val genres: List<ItemGenreEntity> = listOf()
) : Parcelable