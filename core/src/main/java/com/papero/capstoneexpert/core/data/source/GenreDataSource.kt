package com.papero.capstoneexpert.core.data.source

import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import io.reactivex.Flowable

interface GenreDataSource {
    fun <T> getAllGenre(): Flowable<T>

    fun <T> getGenreById(id: Int): T

    fun insertAllGenre(genres: List<GenreEntityDB>)
}