package com.papero.capstoneexpert.core.data.source

import io.reactivex.Flowable

interface NowPlayingDataSource {
    fun <T> getAllNowPlaying(): Flowable<T>

    fun <T> getNowPlayingById(id: Int): T


}