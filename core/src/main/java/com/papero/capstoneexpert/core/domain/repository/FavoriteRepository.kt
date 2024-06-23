package com.papero.capstoneexpert.core.domain.repository

import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface FavoriteRepository {
    fun getAllFavorite(): Flowable<ResultState<List<FavoriteEntity>>>

    fun insertFavorite(entity: FavoriteEntity): Single<Long>

    fun getFavorite(id: Int): Single<Boolean>

    fun deleteFavorite(id: Int): Single<ResultState<Unit>>
}