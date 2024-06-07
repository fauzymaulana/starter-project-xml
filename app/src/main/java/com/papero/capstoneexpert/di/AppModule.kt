package com.papero.capstoneexpert.di

import com.papero.capstoneexpert.core.domain.use_case.NowPlayingUseCaseImpl
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideNowPlayingUsecase(nowPlayingUsecase: NowPlayingUseCaseImpl): NowPlayingUseCase
}