package com.papero.capstoneexpert.core.utilities

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> singleIo(): SingleTransformer<T, T> {
    return SingleTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}