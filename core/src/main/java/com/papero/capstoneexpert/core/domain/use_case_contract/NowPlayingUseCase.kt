package com.papero.capstoneexpert.core.domain.use_case_contract

import com.papero.capstoneexpert.core.domain.model.now_playing.MessageEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface NowPlayingUseCase {
    fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>>

    fun getNowPlayingById(id: Int): Flowable<ResultState<NowPlayingEntity>>

    fun getNowPlayingWithFavorite(id: Int): Observable<ResultState<NowPlayingEntity>>

    fun message(name: String): MessageEntity
}