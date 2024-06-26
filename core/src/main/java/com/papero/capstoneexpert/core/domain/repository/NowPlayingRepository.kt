package com.papero.capstoneexpert.core.domain.repository

import com.papero.capstoneexpert.core.domain.model.now_playing.MessageEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface NowPlayingRepository {
    fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>>

    fun getNowPlayingById(id: Int): Flowable<ResultState<NowPlayingEntity>>

    fun message(name: String): MessageEntity
}