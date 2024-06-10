package com.papero.capstoneexpert.core.domain.use_case

import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.repository.NowPlayingRepository
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingUseCaseImpl @Inject constructor(
    private val repository: NowPlayingRepository
): NowPlayingUseCase {
    override fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>> {
        return repository.getAllNowPlaying()
            .subscribeOn(Schedulers.io())
    }

    override fun getNowPlayingById(id: Int): Flowable<ResultState<NowPlayingEntity>> {
        return repository.getNowPlayingById(id)
            .subscribeOn(Schedulers.io())
    }
}