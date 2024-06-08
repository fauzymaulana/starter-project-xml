package com.papero.capstoneexpert.core.data.source.local.config

import com.papero.capstoneexpert.core.data.source.local.dao.GenreDao
import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: NowPlayingDao,
    private val genreDao: GenreDao
) {

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

    fun getAllGenre(): Flowable<List<GenreEntityDB>> {
        return genreDao.getAllGenre()
    }

    fun insertAllGenre(genres: List<GenreEntityDB>) {
        genreDao.insertAllGenre(genres)
    }
}