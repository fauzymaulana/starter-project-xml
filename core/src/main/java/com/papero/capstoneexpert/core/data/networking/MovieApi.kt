package com.papero.capstoneexpert.core.data.networking

import com.papero.capstoneexpert.core.BuildConfig
import com.papero.capstoneexpert.core.data.source.remote.BaseDataSourceMovieResponse
import com.papero.capstoneexpert.core.data.source.remote.NowPlayingResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(Routes.MOVIE + Routes.NOW_PLAYING)
    fun getNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ): Flowable<BaseDataSourceMovieResponse<List<NowPlayingResponse>>>

}