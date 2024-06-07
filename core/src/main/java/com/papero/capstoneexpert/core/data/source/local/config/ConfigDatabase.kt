package com.papero.capstoneexpert.core.data.source.local.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB

@Database(entities = [NowPlayingEntityDB::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class ConfigDatabase: RoomDatabase()  {
    abstract fun nowPlayingDao(): NowPlayingDao
}