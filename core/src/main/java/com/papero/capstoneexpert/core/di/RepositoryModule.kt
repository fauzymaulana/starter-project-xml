package com.papero.capstoneexpert.core.di

import com.papero.capstoneexpert.core.data.repository.GenreRepositoryImpl
import com.papero.capstoneexpert.core.data.repository.NowPlayingRepositoryImpl
import com.papero.capstoneexpert.core.domain.repository.GenreRepository
import com.papero.capstoneexpert.core.domain.repository.NowPlayingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideNowPlayingRepository(nowPlayingRepository: NowPlayingRepositoryImpl): NowPlayingRepository

    @Binds
    abstract fun provideGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository
}