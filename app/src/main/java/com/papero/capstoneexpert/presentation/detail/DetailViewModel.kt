package com.papero.capstoneexpert.presentation.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.papero.capstoneexpert.core.base.BaseViewModel
import com.papero.capstoneexpert.core.domain.mapper.toFavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.use_case_contract.FavoriteUseCase
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val nowPlayingUsecase : NowPlayingUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : BaseViewModel() {

    private val _movie by lazy { MutableLiveData<ResultState<NowPlayingEntity>>() }
    val movie get() = _movie

    private val _foundMovie by lazy { MutableLiveData<ResultState<Boolean>>() }
    val foundMovie get() = _foundMovie

    private val _saveMovie by lazy { MutableLiveData<ResultState<Long>>() }
    val saveMovie get() = _saveMovie

    private val _deleteMovie by lazy { MutableLiveData<ResultState<Unit>>() }
    val deleteMovie get() = _deleteMovie

    private val _movieId by lazy { MutableLiveData<Int>() }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

    fun compareMovieById() {
//        val disposable = nowPlayingUsecase.
    }
    fun getMovieById() {
        val disposable = nowPlayingUsecase.getNowPlayingById(_movieId.value ?: 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _movie.value = ResultState.Loading()
            }
            .subscribe(
                { res ->
                    _movie.value = ResultState.HideLoading()
                    _movie.value = res
                },
                { err ->
                    _movie.value = ResultState.HideLoading()
                    _movie.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        data = null,
                        code = 0
                    )
                }
            )

        addDisposable(disposable)
    }

    fun getFavoriteById() {
        Log.e("TAG", "getFavoriteById: ekm", )
        val disposable = favoriteUseCase.getFavorite(_movieId.value ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _foundMovie.value = ResultState.Loading()
            }
            .subscribe(
                { res ->
                    _foundMovie.value = ResultState.HideLoading()
                    _foundMovie.value = res
                },
                { err ->
                    _foundMovie.value = ResultState.HideLoading()
                    _foundMovie.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        code = 0,
                        data = null
                    )
                }
            )

        addDisposable(disposable)
    }

    fun addFavorite(e: NowPlayingEntity) {
        Log.e("TAG", "addFavorite: ek", )
        val disposable = favoriteUseCase.insertFavorite(e.toFavoriteEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    _saveMovie.value = ResultState.HideLoading()
                    _saveMovie.value = res
                    Log.e("TAG", "addFavorite: daanya ${res.data}", )
                },
                { err ->
                    _saveMovie.value = ResultState.HideLoading()
                    _saveMovie.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        code = 0,
                        data = null
                    )
                    Log.e("TAG", "addFavorite: err ${err.message}", )
                }
            )

        addDisposable(disposable)
    }

    fun deleteMovieSaved() {
        Log.e("TAG", "deleteMovieSaved() returned: ")
        val disposable = favoriteUseCase.deleteFavorite(_movieId.value ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _deleteMovie.value = ResultState.Loading()
            }
            .subscribe(
                { res ->
                    _deleteMovie.value = ResultState.HideLoading()
                    _deleteMovie.value = res
                    Log.e("APA", "kfanjf $res")
                },
                { err ->
                    _deleteMovie.value = ResultState.HideLoading()
                    _deleteMovie.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        code = 0,
                        data = null
                    )
Log.e("APA", "kfanjf")
                }
            )
        addDisposable(disposable)
    }

    fun fetchNowPlayingWithFavorite() {
        val disposable = nowPlayingUsecase.getNowPlayingWithFavorite(_movieId.value ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { succ ->
                    _movie.value = ResultState.HideLoading()
                    _movie.value = succ
                    Log.e("TAG", "fetchNowPlayingWithFavorite: datanya ada ni cuy ${succ.data}", )
                    Log.e("TAG", "fetchNowPlayingWithFavorite: datanya ada  ${succ}", )
                },
                { err ->
                    _movie.value = ResultState.HideLoading()
                    _movie.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        code = 0,
                        data = null
                    )
                    Log.e("TAG", "fetchNowPlayingWithFavorite: er ${err.message}", )
                }
            )

        addDisposable(disposable)
    }
}