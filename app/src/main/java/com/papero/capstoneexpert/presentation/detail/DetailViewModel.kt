package com.papero.capstoneexpert.presentation.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papero.capstoneexpert.core.base.BaseViewModel
import com.papero.capstoneexpert.core.domain.mapper.toFavoriteEntity
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
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

    private val _favorite by lazy { MutableLiveData<ResultState<FavoriteEntity?>>() }
    val favorite get() = _favorite

    private val _movieId by lazy { MutableLiveData<Int>() }

    fun setMovieId(id: Int) {
        _movieId.value = id
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
        Log.e("TAG", "getFavoriteById: ek", )
        val disposable = favoriteUseCase.getFavorite(_movieId.value ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    _favorite.value = res
                },
                { err ->
                    _favorite.value = ResultState.UnknownError(
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
                {res ->
                    Log.e("TAG", "addFavorite: daanya ${res.data}", )
                },
                { err ->
                    Log.e("TAG", "addFavorite: err ${err.message}", )
                }
            )

        addDisposable(disposable)
    }
}