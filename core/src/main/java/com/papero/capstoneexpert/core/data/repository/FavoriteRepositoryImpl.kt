package com.papero.capstoneexpert.core.data.repository

import android.util.Log
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.domain.mapper.toEntity
import com.papero.capstoneexpert.core.domain.mapper.toEntityDB
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.repository.FavoriteRepository
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.responseErrorToResultStateError
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
            try {
                val insert = localDS.insertNowPlaying(entity.toEntityDB())
                emitter.onSuccess(insert)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun getFavorite(id: Int): Single<Boolean> {
        return Single.create { emitter ->
            val find = localDS.getFavorite(id)
            if (find != null) {
                emitter.onSuccess(true)
            } else {
                emitter.onSuccess(false)
            }
        }
    }

    override fun deleteFavorite(id: Int): Single<ResultState<Unit>> {
        return localDS.deleteFavorite(id)
            .toSingle {
                responseMapsToResultState(Unit) {}
            }
            .onErrorReturn {
                responseErrorToResultStateError(it)
            }
    }
}