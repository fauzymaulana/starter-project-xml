package com.papero.capstoneexpert.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import io.reactivex.Flowable

@Dao
interface NowPlayingDao {

    @Query("SELECT * FROM nowPlayingROOM")
    fun getAllNowPlaying(): Flowable<List<NowPlayingEntityDB>>

    @Query("SELECT * FROM nowPlayingROOM WHERE id = :id")
    fun getNowPlayingById(id: Int): Flowable<NowPlayingEntityDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNowPlaying(nowPlayings: List<NowPlayingEntityDB>)
}