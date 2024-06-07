package com.papero.capstoneexpert.core.data.source

import android.annotation.SuppressLint
import com.bumptech.glide.load.engine.Resource
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import com.papero.capstoneexpert.core.data.source.remote.BaseDataSourceMovieResponse
import com.papero.capstoneexpert.core.data.source.remote.NowPlayingResponse
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<ResultState<ResultType>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        val dbSource = loadFromDB()
            .subscribeOn(Schedulers.io())
            .firstElement()
            .subscribe({ dbValue ->
                if (shouldFetch(dbValue)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(ResultState.Success(dbValue))
                }
            }, { throwable ->
                result.onNext(
                    ResultState.UnknownError(
                        message = throwable.message ?: "Unknown Error",
                        data = null,
                        code = 0
                    )
                )
            })

        compositeDisposable.add(dbSource)
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
            .subscribeOn(Schedulers.io())
            .firstElement()
            .subscribe({ response ->
                when (response) {
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                            .subscribeOn(Schedulers.io())
                            .firstElement()
                            .subscribe({ dbValue ->
                                result.onNext(ResultState.Success(dbValue))
                            }, { throwable ->
                                result.onNext(
                                    ResultState.UnknownError(
                                        message = throwable.message ?: "Unknown Error",
                                        data = null,
                                        code = 0
                                    )
                                )
                            })

                        compositeDisposable.add(dbSource)
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                            .subscribeOn(Schedulers.io())
                            .firstElement()
                            .subscribe({ dbValue ->
                                result.onNext(ResultState.Success(dbValue))
                            }, { throwable ->
                                result.onNext(ResultState.UnknownError(
                                    message = throwable.message ?: "Unknown Error",
                                    data = null,
                                    code = 0))
                            })

                        compositeDisposable.add(dbSource)
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(
                            ResultState.UnknownError(
                                message = response.errorMessage,
                                data = null,
                                code = 0
                            )
                        )
                    }
                }
            }, { throwable ->
                result.onNext(
                    ResultState.UnknownError(
                        message = throwable.message ?: "Unknown Error",
                        data = null,
                        code = 0
                    )
                )
            })

        compositeDisposable.add(apiResponse)
    }

    fun asFlowable(): Flowable<ResultState<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)

    fun clearDisposables() {
        compositeDisposable.clear()
    }
}