package com.papero.capstoneexpert.core.domain.mapper

import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.data.source.remote.now_playing.NowPlayingResponse
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity

fun NowPlayingEntityDB?.toEntity(): NowPlayingEntity {
    return NowPlayingEntity(
        id = this?.id,
        title = this?.title,
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        popularity = this?.popularity,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
        genreIds = emptyList(),
//        genreIds = this?.genreIds?.map { it },
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        adult = this?.adult,
        voteCount = this?.voteCount
    )
}

fun NowPlayingResponse?.toEntityDB(): NowPlayingEntityDB {
    return NowPlayingEntityDB(
        id = this?.id ?: 0,
        title = this?.title ?: "",
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        popularity = this?.popularity,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
//        genreIds = this?.genreIds?.map { it } ?: emptyList(),
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        adult = this?.adult,
        voteCount = this?.voteCount
    )
}