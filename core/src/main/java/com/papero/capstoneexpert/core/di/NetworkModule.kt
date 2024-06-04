package com.papero.capstoneexpert.core.di

import com.papero.capstoneexpert.core.data.networking.MovieApi
import com.papero.capstoneexpert.core.data.networking.RequestClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): RequestClient {
        return RequestClient()
    }

    @Provides
    @Singleton
    fun provideApiService(req : RequestClient): MovieApi {
        return req.getClient().create(MovieApi::class.java)
    }
}