package com.papero.capstoneexpert.core.domain.mapper

import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import com.papero.capstoneexpert.core.data.source.remote.genre.GenreResponse
import com.papero.capstoneexpert.core.data.source.remote.genre.ItemGenreResponse

fun ItemGenreResponse?.toEntityDB(): GenreEntityDB {
    return GenreEntityDB(
        id = this?.id ?: 0,
        name = this?.name.toString()
    )
}