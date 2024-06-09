package com.papero.capstoneexpert.core.domain.use_case

import com.papero.capstoneexpert.core.domain.model.genre.ItemGenreEntity
import com.papero.capstoneexpert.core.domain.repository.GenreRepository
import com.papero.capstoneexpert.core.domain.use_case_contract.GenreUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenreUseCaseImpl @Inject constructor(
    private val repository: GenreRepository
) : GenreUseCase {
    override fun getAllGenre(): Flowable<ResultState<List<ItemGenreEntity>>> {
        return repository.getAllGenre()
            .subscribeOn(Schedulers.io())
    }
}