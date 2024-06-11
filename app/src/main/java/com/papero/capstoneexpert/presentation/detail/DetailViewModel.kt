package com.papero.capstoneexpert.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papero.capstoneexpert.core.base.BaseViewModel
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val nowPlayingUsecase : NowPlayingUseCase
) : BaseViewModel() {

    private val _movie by lazy { MutableLiveData<ResultState<NowPlayingEntity>>() }
    val movie get() = _movie

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

    fun addFavorite() {
//        val disposable = nowPlayingUsecase.
    }
}