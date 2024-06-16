package com.papero.capstoneexpert.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.papero.capstoneexpert.core.data.source.local.entity.FavoriteEntityDB
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteROOM")
    fun getAllFavorite(): Flowable<List<FavoriteEntityDB>>

    @Query("SELECT * FROM favoriteROOM WHERE id = :id")
    fun getFavorite(id: Int): FavoriteEntityDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(movie: FavoriteEntityDB): Long

//    @Delete
//    fun deleteFavorite(id: Int)
}