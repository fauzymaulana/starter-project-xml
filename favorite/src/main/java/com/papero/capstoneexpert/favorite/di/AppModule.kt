package com.papero.capstoneexpert.favorite.di

import com.papero.capstoneexpert.core.domain.use_case.FavoriteUseCaseImpl
import com.papero.capstoneexpert.core.domain.use_case_contract.FavoriteUseCase
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
    abstract fun provideFavoriteUseCase(favoriteUseCase: FavoriteUseCaseImpl): FavoriteUseCase
}