package com.papero.capstoneexpert.core.domain.use_case_contract

import com.papero.capstoneexpert.core.domain.model.genre.ItemGenreEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable

interface GenreUseCase {
    fun getAllGenre(): Flowable<ResultState<List<ItemGenreEntity>>>
}