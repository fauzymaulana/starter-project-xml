package com.papero.capstoneexpert.core.data.source.remote.genre

import com.google.gson.annotations.SerializedName

data class ItemGenreResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
