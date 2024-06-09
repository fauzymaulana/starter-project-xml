package com.papero.capstoneexpert.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import io.reactivex.Flowable

@Dao
interface GenreDao {

    @Query("SELECT * FROM genreROOM")
    fun getAllGenre(): Flowable<List<GenreEntityDB>>

    @Query("SELECT * FROM genreROOM WHERE id = :id")
    fun getGenreById(id: Int): GenreEntityDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenre(genres: List<GenreEntityDB>)
}