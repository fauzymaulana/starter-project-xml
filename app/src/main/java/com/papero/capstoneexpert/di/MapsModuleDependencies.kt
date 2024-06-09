package com.papero.capstoneexpert.di

import com.papero.capstoneexpert.core.domain.use_case_contract.GenreUseCase
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MapsModuleDependencies {
    fun nowPlayingUsecase(): NowPlayingUseCase
    fun genreUsecase(): GenreUseCase
}