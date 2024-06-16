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
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.String
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
        return Single.create { emitter ->
            Log.e("TAG", "insertFavorite: fav ", )
            try {
                val insert = localDS.insertNowPlaying(entity.toEntityDB())
                emitter.onSuccess(insert)
            } catch (e: Exception) {
                Log.e("TAG", "insertFavorite: err ${e.message.toString()}", )
                emitter.onError(e)
            }
        }
//        Flowable.create({ emitter ->
//            val a = localDS.insertNowPlaying(entity.toEntityDB())
//            emitter.onNext(a)
//        })
//        return localDS.insertNowPlaying(entity.toEntityDB())
//            .subscribeOn(Schedulers.io())
    }

    override fun getFavorite(id: Int): Single<Boolean> {
        Log.e("TAG", "getFavorite: REPO", )
        return Single.create { emitter ->
            val find = localDS.getFavorite(id)
            if (find != null) {
                emitter.onSuccess(true)
            } else {
                emitter.onSuccess(false)
            }
        }
//        return localDS.getFavorite(id)
//            .subscribeOn(Schedulers.io())
//            .map {
//                it.toEntity()
//            }
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

//    override fun deleteFavorite(id: Int): Single<Unit> {
//        return Single.create { emitter ->
//            emitter.onSuccess(localDS.deleteFavorite(id))
////            Log.e("TAG", "deleteFavorite: data delete $delete", )
////            if (delete == 0) {
////                emitter.onSuccess(true)
////            } else {
////                emitter.onSuccess(false)
////            }
//        }
//
//    }
}