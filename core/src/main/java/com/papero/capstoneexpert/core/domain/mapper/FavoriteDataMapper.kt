package com.papero.capstoneexpert.core.domain.mapper

import com.papero.capstoneexpert.core.data.source.local.entity.FavoriteEntityDB
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity

fun FavoriteEntityDB?.toEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = this?.id,
        title = this?.title,
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        popularity = this?.popularity,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
        genreIds = mutableListOf(),//this?.genreIds,
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        adult = this?.adult,
        voteCount = this?.voteCount
    )
}

fun FavoriteEntity?.toEntityDB(): FavoriteEntityDB {
    return FavoriteEntityDB(
        id = this?.id ?: 0,
        title = this?.title ?: "",
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        popularity = this?.popularity,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        adult = this?.adult,
        voteCount = this?.voteCount
    )
}

fun FavoriteEntity?.toNowPlayingEntity(): NowPlayingEntity {
    return NowPlayingEntity(
        id = this?.id ?: 0,
        title = this?.title ?: "",
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        popularity = this?.popularity,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        adult = this?.adult,
        voteCount = this?.voteCount,
        genreIds = mutableListOf()
    )
}