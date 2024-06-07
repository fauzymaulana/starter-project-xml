package com.papero.capstoneexpert.core.data.repository

import com.papero.capstoneexpert.core.data.source.NetworkBoundResource
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.data.source.remote.NowPlayingResponse
import com.papero.capstoneexpert.core.data.source.remote.RemoteDataSource
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.repository.NowPlayingRepository
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingRepositoryImpl @Inject constructor(
    private val remoteDS: RemoteDataSource,
    private val localDS: LocalDataSource
) : NowPlayingRepository {
    override fun getAllNowPlaying(): Flowable<ResultState<List<NowPlayingEntity>>> {
        val result = object :NetworkBoundResource<List<NowPlayingEntity>, List<NowPlayingResponse>>() {
            override fun loadFromDB(): Flowable<List<NowPlayingEntity>> {
                return localDS.getAllNowPlaying().map {
                    it.toListEntity()
                }
            }

            override fun createCall(): Flowable<ApiResponse<List<NowPlayingResponse>>> {
                return remoteDS.getNowPlaying()
            }

            override fun saveCallResult(data: List<NowPlayingResponse>) {
                val nowPlayingList = data.toListEntity<NowPlayingResponse, NowPlayingEntityDB>()
                localDS.insertAllNowPlaying(nowPlayingList)
            }

            override fun shouldFetch(data: List<NowPlayingEntity>?): Boolean {
                return true
            }
        }
        return result.asFlowable()
    }
}
