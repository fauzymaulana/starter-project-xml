package com.papero.capstoneexpert.core.data.source.remote

import android.util.Log
import com.papero.capstoneexpert.core.data.networking.MovieApi
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val api: MovieApi) {
    fun getNowPlaying(): Flowable<ApiResponse<List<NowPlayingResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<NowPlayingResponse>>>()

        val client = api.getNowPlaying()

        client
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
                    Log.e("RemoteDataSource=", "getNowPlaying: err ${err.message}", )
                }
            )

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}