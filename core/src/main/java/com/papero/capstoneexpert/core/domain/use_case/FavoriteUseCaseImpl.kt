package com.papero.capstoneexpert.core.domain.use_case

import android.util.Log
import com.papero.capstoneexpert.core.domain.mapper.toNowPlayingEntity
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.repository.FavoriteRepository
import com.papero.capstoneexpert.core.domain.use_case_contract.FavoriteUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.responseMapsToResultState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoriteRepository
) : FavoriteUseCase {
    override fun getAllFavorite(): Flowable<ResultState<List<FavoriteEntity>>> {
        return repository.getAllFavorite()
    }

    override fun insertFavorite(e: FavoriteEntity): Single<ResultState<Long>> {
        Log.e("TAG", "insertFavorite: ini maso", )
        return repository.insertFavorite(e)
            .map {
                return@map responseMapsToResultState(it) { ent ->
                    ent
                }
            }
    }

    override fun getFavorite(id: Int): Single<ResultState<Boolean>> {
        Log.e("TAG", "getFavorite: usecase ", )
        return repository.getFavorite(id)
            .map {
                Log.e("TAG", "getFavorite: ini ${it.toString()}", )
                return@map responseMapsToResultState(it) { ent -> ent}
            }
    }

//    override fun deleteFavorite(id: Int): Single<ResultState<Unit>> {
//        return repository.deleteFavorite(id)
//            .map {
//                responseMapsToResultState(it) { value -> value}
//            }
//    }
}