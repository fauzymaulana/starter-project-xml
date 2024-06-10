package com.papero.capstoneexpert.core.domain.use_case_contract

import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable

interface NowPlayingUseCase {
    fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>>

    fun getNowPlayingById(id: Int): Flowable<ResultState<NowPlayingEntity>>
}