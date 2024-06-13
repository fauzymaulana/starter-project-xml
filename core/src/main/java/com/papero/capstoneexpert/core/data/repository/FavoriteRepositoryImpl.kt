package com.papero.capstoneexpert.core.data.repository

import android.util.Log
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.domain.mapper.toEntity
import com.papero.capstoneexpert.core.domain.mapper.toEntityDB
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.repository.FavoriteRepository
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.responseMapsToResultState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource
) : FavoriteRepository {
    override fun getAllFavorite(): Flowable<ResultState<List<FavoriteEntity>>> {
        return localDS.getAllFavorite()
            .map { res ->
                return@map responseMapsToResultState(res) {
                    it.toListEntity()
                }
            }
    }

    override fun insertFavorite(entity: FavoriteEntity): Single<Long> {
        return localDS.insertNowPlaying(entity.toEntityDB())
            .subscribeOn(Schedulers.io())
    }

    override fun getFavorite(id: Int): Flowable<FavoriteEntity?> {
        return localDS.getFavorite(id)
            .subscribeOn(Schedulers.io())
            .map {
                it.toEntity()
            }
    }

//        return localDS.getFavorite(entity.id ?: 0)
//            .map {
//
//            }
//            .flatMap {
//                if (it == null) {
//                    localDS.insertNowPlaying(entity.toEntityDB())
//                } else {
//                    -1L
//                }
//            }
//    }
//        return  Single.create { emit ->
//            localDS.getFavorite(entity.id ?: 0)
//                .subscribe {
//                    if (it == null) {
//                        localDS.insertNowPlaying(entity.toEntityDB())
//                            .map { id ->
//                                emit.onSuccess(id)
//                            }
//                    } else {
//                        emit.onSuccess(0)
//                    }
//                }
//        }
//        return localDS.insertNowPlaying(entity.toEntityDB())
//            .subscribeOn(Schedulers.io())

//            .flatMapCompletable { favorite ->
//                if (favorite == null) {
//                    localDS.insertNowPlaying(entity.toEntityDB())
//                } else {
//                    Completable.complete()
//                }
//            }
//            .toFlowable<Boolean?>()
//            .map { true }
//            .onErrorReturn { false }
//    }

//    override fun deleteFavorite(id: Int) {
//        return localDS.deleteFavorite(id)
//
//    }
}