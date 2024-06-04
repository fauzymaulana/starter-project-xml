package com.papero.capstoneexpert.core.utilities

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.papero.capstoneexpert.core.base.BaseDataSourceResponse
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

fun <T, R> responseBaseDataSourceApiToResultState(
    baseDataSourceApi: BaseDataSourceResponse<T>,
    onSuccess: (T) -> R,
): ResultState<R> {
    when (baseDataSourceApi.statusCode) {
        200 -> {
            val data = baseDataSourceApi.data
            return if (data != null) {
                onSuccess(data)
                ResultState.Success(onSuccess(data))
            } else {
                ResultState.UnknownError(
                    baseDataSourceApi.status ?: "",
                    null, baseDataSourceApi.statusCode
                )
            }
        }
        400 -> {
            return ResultState.BadRequest(
                baseDataSourceApi.status ?: "",
                null, baseDataSourceApi.statusCode
            )
        }
        401 -> {
            return ResultState.Unauthorized(
                baseDataSourceApi.status ?: "",
                null, baseDataSourceApi.statusCode
            )
        }
        404 -> {
            return ResultState.NotFound(
                baseDataSourceApi.status ?: "",
                null, baseDataSourceApi.statusCode
            )
        }
        403 -> {
            return ResultState.Forbidden(
                baseDataSourceApi.status ?: "",
                null, baseDataSourceApi.statusCode
            )
        }
        409 -> {
            return ResultState.Conflict(
                baseDataSourceApi.status ?: "",
                null, baseDataSourceApi.statusCode
            )
        }
        else -> {
            val data = baseDataSourceApi.data
            return if (data != null) {
                onSuccess(data)
                ResultState.Success(onSuccess(data))
            } else {
                ResultState.UnknownError(
                    baseDataSourceApi.status ?: "",
                    null, baseDataSourceApi.statusCode ?: 0
                )
            }
        }
    }

}

fun <R> responseErrorToResultStateError(
    error: Throwable,
): ResultState<R> {

    when (error) {
        is HttpException -> {
            when (error.code()) {
                400 -> {
                    return ResultState.BadRequest(
                        getMessageFromBody(
                            error
                        ) + " [${error.code()}]", null, code = error.code()
                    )

                }
                401 -> {
                    return ResultState.Unauthorized(
                        getMessageFromBody(
                            error
                        ) + " [${error.code()}]", null, code = error.code()
                    )

                }
                404, 4041 -> {
                    return ResultState.NotFound(
                        getMessageFromBody(
                            error
                        ) + " [${error.code()}]", null, code = error.code()
                    )

                }
                403, 4032 -> {
                    return ResultState.Forbidden(
                        getMessageFromBody(
                            error
                        ), null, code = error.code()
                    )
                }
                409, 406 -> {
                    return ResultState.Conflict(
                        getMessageFromBody(
                            error
                        ), null, code = error.code()
                    )

                }
                else -> {
                    return if (error.code() >= 500) {
                        ResultState.UnknownError(
                            "Maaf, Terjadi gangguan pada server [${error.code()}]",
                            null, code = error.code()
                        )
                    } else {
                        ResultState.UnknownError(
                            getMessageFromBody(
                                error
                            ) + " [${error.code()}]", null,
                            code = error.code()
                        )
                    }

                }
            }
        }
        is RetrofitException -> {
            return when (error.code) {
                1 -> {
                    ResultState.NoConnection(
                        "Tidak ada koneksi internet", null, error.code
                    )
                }
                2 -> {
                    ResultState.UnknownError(
                        "Terjadi gangguan pada aplikasi [00${error.code}]", null,
                        error.code
                    )
                }
                else -> {
                    ResultState.UnknownError(
                        "Terjadi gangguan pada aplikasi [00${error.code}]", null,
                        error.code ?: 0
                    )
                }
            }

        }
        is IOException -> {
            return ResultState.NoConnection(
                "Jaringan kamu sedang tidak stabil", null, 0
            )
        }
        is TimeoutException -> {
            Log.e("RESULT MAPPER", "responseErrorToResultStateError: timesoutnya tereksk yang di map", )
            return ResultState.Timeout(
                "Waktu Akses Habis", null, 0
            )
        }
        else -> {
            return ResultState.UnknownError(
                "Maaf, Terjadi gangguan pada server", null, 0
            )
        }
    }

}

private fun getMessageFromBody(throwable: HttpException): String {
    val type = object : TypeToken<BaseDataSourceResponse<String>>() {}.type
    var errorResponse: BaseDataSourceResponse<String>? = null
    return try {
        throwable.response()?.errorBody()?.let {
            errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.charStream(), type)

        }
        errorResponse?.status ?: "Terjadi gangguan pada server"

    } catch (error: Exception) {
        "Terjadi gangguan pada server"
    }
}

fun <T, R> responseMapsToResultState(
    routeEntity: T,
    onSuccess: (T) -> R,
): ResultState<R> {
    onSuccess(routeEntity)
    return ResultState.Success(onSuccess(routeEntity))
}