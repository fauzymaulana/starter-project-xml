package com.papero.capstoneexpert.core.data.source.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import io.reactivex.Flowable

interface GenreDao {

    @Query("SELECT * FROM genreROOM")
    fun getAllGenre(): Flowable<List<GenreEntityDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenre(genres: List<GenreEntityDB>)
}