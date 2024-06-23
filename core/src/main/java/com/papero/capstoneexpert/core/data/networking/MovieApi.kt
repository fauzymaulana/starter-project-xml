package com.papero.capstoneexpert.core.data.networking

import com.papero.capstoneexpert.core.BuildConfig
import com.papero.capstoneexpert.core.data.networking.Key.API_KEY
import com.papero.capstoneexpert.core.data.networking.Key.MOVIE_ID
import com.papero.capstoneexpert.core.data.networking.Key.PAGE
import com.papero.capstoneexpert.core.data.source.remote.BaseDataSourceMovieResponse
import com.papero.capstoneexpert.core.data.source.remote.genre.GenreResponse
import com.papero.capstoneexpert.core.data.source.remote.now_playing.NowPlayingResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(Route.VERSION + Route.GENRE + "${Route.MOVIE}" + Route.LIST)
    fun getGenres(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Flowable<GenreResponse>

    @GET(Route.VERSION + Route.MOVIE + Route.NOW_PLAYING)
    fun getNowPlaying(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(PAGE) page: Int = 1
    ): Flowable<BaseDataSourceMovieResponse<List<NowPlayingResponse>>>

    @GET(Route.VERSION + "${Route.MOVIE}" + "/{$MOVIE_ID}")
    fun getMovieById(
        @Path(MOVIE_ID) movieId: Int
    ): Flowable<NowPlayingResponse>

}