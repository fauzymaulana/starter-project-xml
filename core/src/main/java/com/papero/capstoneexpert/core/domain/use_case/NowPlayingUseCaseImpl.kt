package com.papero.capstoneexpert.core.domain.use_case

import com.papero.capstoneexpert.core.domain.mapper.toEntityDB
import com.papero.capstoneexpert.core.domain.mapper.toFavoriteEntity
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.MessageEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.repository.FavoriteRepository
import com.papero.capstoneexpert.core.domain.repository.NowPlayingRepository
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.responseMapsToResultState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingUseCaseImpl @Inject constructor(
    private val repository: NowPlayingRepository,
    private val favRepo: FavoriteRepository
): NowPlayingUseCase {
    override fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>> {
        return repository.getAllNowPlaying()
            .subscribeOn(Schedulers.io())
    }

    override fun getNowPlayingById(id: Int): Flowable<ResultState<NowPlayingEntity>> {
        return repository.getNowPlayingById(id)
            .subscribeOn(Schedulers.io())
    }

    override fun getNowPlayingWithFavorite(id: Int): Observable<ResultState<NowPlayingEntity>> {
        return Observable.zip(
            repository.getNowPlayingById(id).toObservable(),
            favRepo.getFavorite(id).toObservable()
        ) { movie, favorite ->

            movie.data?.let { movv ->
                if (favorite) {
                    responseMapsToResultState(movv) { value ->
                        value.toFavoriteEntity(true)
                    }
                } else {
                    responseMapsToResultState(movv) { value ->
                        value
                    }
                }
            }!!
        }
    }

    override fun message(name: String): MessageEntity {
        return repository.message(name)
    }
}