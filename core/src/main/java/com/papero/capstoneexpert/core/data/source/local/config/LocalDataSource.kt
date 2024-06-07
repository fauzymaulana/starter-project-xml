package com.papero.capstoneexpert.core.data.source.local.config

import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: NowPlayingDao) {

    fun getAllNowPlaying(): Flowable<List<NowPlayingEntityDB>> {
        return movieDao.getAllNowPlaying()
            .subscribeOn(Schedulers.io())
    }

    fun getNowPlayingById(id: Int): Flowable<NowPlayingEntityDB> {
        return movieDao.getNowPlayingById(id)
    }

    fun insertAllNowPlaying(nowPlayings: List<NowPlayingEntityDB>) {
        movieDao.insertAllNowPlaying(nowPlayings)
    }
}