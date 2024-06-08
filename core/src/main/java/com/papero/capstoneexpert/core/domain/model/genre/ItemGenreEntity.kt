package com.papero.capstoneexpert.core.domain.model.genre

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemGenreEntity(
    val id : Int?,
    val name : String?
): Parcelable