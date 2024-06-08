package com.papero.capstoneexpert.core.data.source.remote.now_playing

import com.google.gson.annotations.SerializedName

data class DateRangeResponse(
    @field:SerializedName("maximum")
    val maximum: String?,

    @field:SerializedName("minimum")
    val minimum: String?,
)