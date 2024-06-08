package com.papero.capstoneexpert.core.data.source.remote

import android.util.Log
import com.papero.capstoneexpert.core.data.networking.MovieApi
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import com.papero.capstoneexpert.core.data.source.remote.genre.ItemGenreResponse
import com.papero.capstoneexpert.core.data.source.remote.now_playing.NowPlayingResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val api: MovieApi) {
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun fetN(page: Int): Flowable<List<NowPlayingResponse>> {
        val resultData = PublishSubject.create<ApiResponse<List<NowPlayingResponse>>>()

        val client = api.getNowPlaying(page = page)
        val disposable = client
            .subscribeOn(Schedulers.io())
//            .take(1)
//            .subscribe(
//                { res ->
//                    val dataArray = res.results
//                    if (dataArray.isNotEmpty()) {
//                        resultData.onNext(ApiResponse.Success(dataArray))
//                    } else {
//                        resultData.onNext(ApiResponse.Empty)
//                    }
//                    resultData.onComplete()
//                },
//                { err ->
//                    resultData.onNext(ApiResponse.Error(err.message.toString()))
//                    Log.e("RemoteDataSource=", "getNowPlaying: err ${err.message}")
//                }
//            )

//        compositeDisposable.add(disposable)
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
    fun getNowPlaying(): Flowable<ApiResponse<List<NowPlayingResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<NowPlayingResponse>>>()

        val client = api.getNowPlaying()

        val disposable = client
            .subscribeOn(Schedulers.io())
            .take(1)
            .subscribe(
                { res ->
                    val dataArray = res.results
                    if (dataArray.isNotEmpty()) {
                        resultData.onNext(ApiResponse.Success(dataArray))
                    } else {
                        resultData.onNext(ApiResponse.Empty)
                    }
                    resultData.onComplete()
                },
                { err ->
                    resultData.onNext(ApiResponse.Error(err.message.toString()))
                    Log.e("RemoteDataSource=", "getNowPlaying: err ${err.message}")
                }
            )

        compositeDisposable.add(disposable)
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getAllGenre(): Flowable<ApiResponse<List<ItemGenreResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<ItemGenreResponse>>>()

        val disposable = api.getGenres()
            .subscribeOn(Schedulers.io())
            .take(1)
            .subscribe(
                { res ->
                    val dataGenres = res.genres
                    if (dataGenres.isNotEmpty()) {
                        resultData.onNext(ApiResponse.Success(dataGenres))
                    } else {
                        resultData.onNext(ApiResponse.Empty)
                    }
                    resultData.onComplete()
                }, { err ->
                    resultData.onNext(ApiResponse.Error(err.message.toString()))
                    Log.e("RemoteDataSource=", "getAllGenre: err ${err.message}")
                }
            )

        compositeDisposable.add(disposable)
        return resultData.toFlowable(BackpressureStrategy.BUFFER)

    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}