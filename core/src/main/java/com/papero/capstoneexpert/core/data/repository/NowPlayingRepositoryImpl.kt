package com.papero.capstoneexpert.core.data.repository

import com.papero.capstoneexpert.core.data.source.NetworkBoundResource
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.data.source.remote.now_playing.NowPlayingResponse
import com.papero.capstoneexpert.core.data.source.remote.RemoteDataSource
import com.papero.capstoneexpert.core.domain.mapper.toEntity
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.repository.NowPlayingRepository
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import retrofit2.HttpException
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
                val temp = mutableListOf<String>()
                val tempMovie = mutableListOf<NowPlayingEntity>()
                data.map { nowPlaying ->
                    nowPlaying.genreIds?.map { id ->
                        val genre = localDS.getGenreById(id)
                        temp.add(genre.name)
                    }
                    tempMovie.add(
                        nowPlaying.toEntity(temp)
                    )
                }
                val a = tempMovie.toList()
                val nowPlayingList = a.toListEntity<NowPlayingEntity, NowPlayingEntityDB>()
                localDS.insertAllNowPlaying(nowPlayingList)
            }

            override fun shouldFetch(data: List<NowPlayingEntity>?): Boolean {
                return true
            }
        }
        return result.asFlowable()
    }
}
