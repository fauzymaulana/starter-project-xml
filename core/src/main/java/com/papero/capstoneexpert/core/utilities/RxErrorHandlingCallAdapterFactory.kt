package com.papero.capstoneexpert.core.utilities

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import java.io.IOException
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.create()
    }

    companion object {
        lateinit var context: Context

        fun create(context: Context): CallAdapter.Factory {
            Companion.context = context
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *> {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        private val wrapped: CallAdapter<R, *>,
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type = wrapped.responseType()

        override fun adapt(call: Call<R>): Any {
            val adapted = wrapped.adapt(call)
            if (adapted is Observable<*>) {
                return adapted.onErrorResumeNext(Function { Observable.error(asRetrofitException(it)) })
            }
            if (adapted is Maybe<*>) {
                return adapted.onErrorResumeNext(Function { Maybe.error(asRetrofitException(it)) })

            }
            if (adapted is Single<*>) {
                return adapted.onErrorResumeNext {
                    return@onErrorResumeNext Single.error(asRetrofitException(it))
                }
            }
            if (adapted is Completable) {
                return adapted.onErrorResumeNext {
                    return@onErrorResumeNext Completable.error(asRetrofitException(it))
                }
            }
            return adapted
        }


        private fun asRetrofitException(throwable: Throwable): Throwable {


            // A network error happened
            if (throwable is IOException) {
                return RetrofitException.networkError(throwable)
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return throwable
        }

    }
}