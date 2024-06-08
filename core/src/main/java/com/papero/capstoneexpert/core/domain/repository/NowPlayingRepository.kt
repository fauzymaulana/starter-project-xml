package com.papero.capstoneexpert.core.domain.repository

import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable

interface NowPlayingRepository {
    fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>>
}