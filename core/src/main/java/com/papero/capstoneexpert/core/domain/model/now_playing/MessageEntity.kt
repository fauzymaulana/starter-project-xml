package com.papero.capstoneexpert.core.domain.model.now_playing

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageEntity(
    val message: String
): Parcelable
