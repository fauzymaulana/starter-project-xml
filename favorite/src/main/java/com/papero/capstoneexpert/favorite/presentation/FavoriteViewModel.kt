package com.papero.capstoneexpert.favorite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papero.capstoneexpert.core.base.BaseViewModel
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.use_case_contract.FavoriteUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor (
//    private val favoriteUsecase: FavoriteUseCase
) : BaseViewModel() {

//    private val _favoriteList by lazy { MutableLiveData<ResultState<List<FavoriteEntity>>>() }
//    val favoriteList get() = _favoriteList
//
//    fun getAllFavorite() {
//        val disposable = favoriteUsecase.getAllFavorite()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                _favoriteList.value = ResultState.Loading()
//            }
//            .subscribe(
//                { res ->
//                    _favoriteList.value = ResultState.HideLoading()
//                    _favoriteList.value = res
//                },
//                { err ->
//                    _favoriteList.value = ResultState.HideLoading()
//                    _favoriteList.value = ResultState.UnknownError(
//                        message = err.message.toString(),
//                        data = null,
//                        code = 0
//                    )
//                }
//            )
//
//        addDisposable(disposable)
//    }
}