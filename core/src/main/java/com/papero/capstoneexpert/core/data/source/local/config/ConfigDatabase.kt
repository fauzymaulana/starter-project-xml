package com.papero.capstoneexpert.core.data.source.local.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.papero.capstoneexpert.core.data.source.local.dao.FavoriteDao
import com.papero.capstoneexpert.core.data.source.local.dao.GenreDao
import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import com.papero.capstoneexpert.core.data.source.local.entity.FavoriteEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB

@Database(
    entities = [
        NowPlayingEntityDB::class,
        GenreEntityDB::class,
        FavoriteEntityDB::class
    ], version = 1, exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class ConfigDatabase: RoomDatabase()  {
    abstract fun nowPlayingDao(): NowPlayingDao
    abstract fun genreDao(): GenreDao
    abstract fun favoriteDao(): FavoriteDao
}