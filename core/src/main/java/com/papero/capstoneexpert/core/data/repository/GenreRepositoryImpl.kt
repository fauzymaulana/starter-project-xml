package com.papero.capstoneexpert.core.data.repository

import com.papero.capstoneexpert.core.data.source.NetworkBoundResource
import com.papero.capstoneexpert.core.data.source.local.config.ApiResponse
import com.papero.capstoneexpert.core.data.source.local.config.LocalDataSource
import com.papero.capstoneexpert.core.data.source.local.entity.GenreEntityDB
import com.papero.capstoneexpert.core.data.source.remote.RemoteDataSource
import com.papero.capstoneexpert.core.data.source.remote.genre.GenreResponse
import com.papero.capstoneexpert.core.data.source.remote.genre.ItemGenreResponse
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.genre.GenreEntity
import com.papero.capstoneexpert.core.domain.model.genre.ItemGenreEntity
import com.papero.capstoneexpert.core.domain.repository.GenreRepository
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepositoryImpl @Inject constructor(
    private val remoteDS: RemoteDataSource,
    private val localDS: LocalDataSource
) : GenreRepository {
    override fun getAllGenre(): Flowable<ResultState<List<ItemGenreEntity>>> {
        val result = object : NetworkBoundResource<List<ItemGenreEntity>, List<ItemGenreResponse>>() {
            override fun loadFromDB(): Flowable<List<ItemGenreEntity>> {
                return localDS.getAllGenre().map { it.toListEntity() }
            }

            override fun createCall(): Flowable<ApiResponse<List<ItemGenreResponse>>> {
                return remoteDS.getAllGenre()
            }

            override fun saveCallResult(data: List<ItemGenreResponse>) {
                val genreList = data.toListEntity<ItemGenreResponse, GenreEntityDB>()
                localDS.insertAllGenre(genreList)
            }

            override fun shouldFetch(data: List<ItemGenreEntity>?): Boolean {
                return true
            }
        }
        return result.asFlowable()
    }
}