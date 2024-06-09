package com.papero.capstoneexpert.core.data.source.remote.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<ItemGenreResponse> = emptyList()
)