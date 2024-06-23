package com.papero.capstoneexpert.core.domain.use_case_contract

import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface FavoriteUseCase {
    fun getAllFavorite(): Flowable<ResultState<List<FavoriteEntity>>>

    fun insertFavorite(e: FavoriteEntity): Single<ResultState<Long>>

    fun getFavorite(id: Int): Single<ResultState<Boolean>>

    fun deleteFavorite(id: Int): Single<ResultState<Unit>>
}