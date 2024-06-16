package com.papero.capstoneexpert.core.data.source.local.config

import com.papero.capstoneexpert.core.data.source.local.dao.FavoriteDao
import com.papero.capstoneexpert.core.data.source.local.dao.GenreDao
import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import com.papero.capstoneexpert.core.data.source.local.entity.FavoriteEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: NowPlayingDao,
    private val genreDao: GenreDao,
    private val favoriteDao: FavoriteDao
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

    fun getGenreById(id: Int):GenreEntityDB {
        return genreDao.getGenreById(id)
    }

    fun insertAllGenre(genres: List<GenreEntityDB>) {
        genreDao.insertAllGenre(genres)
    }

    fun getAllFavorite(): Flowable<List<FavoriteEntityDB>> {
        return favoriteDao.getAllFavorite()
    }

    fun insertNowPlaying(e: FavoriteEntityDB): Long {
        return favoriteDao.insertFavorite(e)
    }

    fun getFavorite(id: Int): FavoriteEntityDB? {
        return favoriteDao.getFavorite(id)
    }

//    fun deleteFavorite(id:Int) {
//        return favoriteDao.deleteFavorite(id)
//    }
}