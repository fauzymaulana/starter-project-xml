package com.papero.capstoneexpert.presentation.home

import androidx.lifecycle.MutableLiveData
import com.papero.capstoneexpert.core.base.BaseViewModel
import com.papero.capstoneexpert.core.domain.model.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: NowPlayingUseCase
) : BaseViewModel() {
    private val _nowPlaying by lazy { MutableLiveData<ResultState<List<NowPlayingEntity>>>() }
    val nowPlaying get() = _nowPlaying

    fun getNowPlaying() {
        val disposable = usecase.getAllNowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _nowPlaying.value = ResultState.Loading()
            }
            .subscribe(
                { res ->
                    _nowPlaying.value = ResultState.HideLoading()
                    _nowPlaying.value = res
                },
                { err ->
                    _nowPlaying.value = ResultState.HideLoading()
                    _nowPlaying.value = ResultState.UnknownError(
                        message = err.message.toString(),
                        data = null,
                        code = 0
                    )
                }
            )

        addDisposable(disposable)
    }
}