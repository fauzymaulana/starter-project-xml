package com.papero.capstoneexpert.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nowPlayingROOM")
data class NowPlayingEntityDB(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "adult")
    @Nullable
    val adult: Boolean? = null,

    @ColumnInfo(name = "backdrop_path")
    @Nullable
    val backdropPath: String? = null,

//    @ColumnInfo(name = "genre_ids")
//    val genreIds: List<Int> = emptyList(),

    @ColumnInfo(name = "original_language")
    @Nullable
    val originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    @Nullable
    val originalTitle: String? = null,

    @ColumnInfo(name = "overview")
    @Nullable
    val overview: String? = null,

    @ColumnInfo(name = "popularity")
    @Nullable
    val popularity: Float? = null,

    @ColumnInfo(name = "poster_path")
    @Nullable
    val posterPath: String? = null,

    @ColumnInfo(name = "release_date")
    @Nullable
    val releaseDate: String? = null,

    @ColumnInfo(name = "vote_average")
    @Nullable
    val voteAverage: Float? = null,

    @ColumnInfo(name = "vote_count")
    @Nullable
    val voteCount: Int? = null
)
