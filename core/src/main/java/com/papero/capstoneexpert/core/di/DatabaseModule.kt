package com.papero.capstoneexpert.core.di

import android.content.Context
import androidx.room.Room
import com.papero.capstoneexpert.core.data.source.local.config.ConfigDatabase
import com.papero.capstoneexpert.core.data.source.local.dao.FavoriteDao
import com.papero.capstoneexpert.core.data.source.local.dao.GenreDao
import com.papero.capstoneexpert.core.data.source.local.dao.NowPlayingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ConfigDatabase {
        return Room.databaseBuilder(
            context,
            ConfigDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNowPlayingDao(database: ConfigDatabase): NowPlayingDao {
        return database.nowPlayingDao()
    }

    @Provides
    fun provideGenreDao(database: ConfigDatabase): GenreDao {
        return database.genreDao()
    }

    @Provides
    fun provideFavoriteDao(database: ConfigDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}