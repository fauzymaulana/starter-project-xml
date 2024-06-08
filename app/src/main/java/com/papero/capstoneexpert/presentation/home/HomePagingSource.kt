package com.papero.capstoneexpert.presentation.home

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.papero.capstoneexpert.core.data.networking.MovieApi
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.data.source.remote.RemoteDataSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

private const val STARTING_PAGE_INDEX = 1

class HomePagingSource(
    private val remoteDS: RemoteDataSource,
    private val localDS: LocalDataSource
): RxPagingSource<Int, NowPlayingEntityDB>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, NowPlayingEntityDB>> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return remoteDS.fetN(page)
            .subscribeOn(Schedulers.io())
            .map { res ->
                val movies = res.results.map { it.toEntity() }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, NowPlayingEntityDB>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun toLoadResult(
        data: MutableList<NowPlayingEntityDB>,
        page: Int?
    ): LoadResult<Int, NowPlayingEntityDB> {
        return if (page != null) {
            LoadResult.Page(
                data = data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } else {
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = null
            )
        }
    }
}