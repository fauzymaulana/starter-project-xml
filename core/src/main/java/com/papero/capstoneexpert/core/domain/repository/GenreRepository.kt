package com.papero.capstoneexpert.core.domain.repository

import com.papero.capstoneexpert.core.domain.model.genre.GenreEntity
import com.papero.capstoneexpert.core.domain.model.genre.ItemGenreEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable

interface GenreRepository {
    fun getAllGenre(): Flowable<ResultState<List<ItemGenreEntity>>>
}