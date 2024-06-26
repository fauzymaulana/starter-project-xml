package com.papero.capstoneexpert.domain

import com.papero.capstoneexpert.core.data.repository.FavoriteRepositoryImpl
import com.papero.capstoneexpert.core.data.repository.NowPlayingRepositoryImpl
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.domain.use_case.NowPlayingUseCaseImpl
import com.papero.capstoneexpert.core.domain.use_case_contract.NowPlayingUseCase
import com.papero.capstoneexpert.core.utilities.ResultState
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NowPlayingUseCaseTest {
    private lateinit var nowPlayingUseCase: NowPlayingUseCase

    @Mock
    private lateinit var nowPlayingRepository: NowPlayingRepositoryImpl
    @Mock
    private lateinit var favoriteRepository: FavoriteRepositoryImpl
    private lateinit var dummyEntity: NowPlayingEntity

    @Before
    fun setUp() {
        nowPlayingUseCase = NowPlayingUseCaseImpl(nowPlayingRepository, favoriteRepository)
        dummyEntity = NowPlayingEntity(
            adult = false,
            backdropPath = "/nxxCPRGTzxUH8SFMrIsvMmdxHti.jpg",
            genreIds = mutableListOf("Comedy","Fantasy","Family"),
            id = 10751,
            originalLanguage = "en",
            originalTitle = "IF",
            overview = "A young girl who goes through a difficult experience begins to see everyone's imaginary friends who have been left behind as their real-life friends have grown up.",
            popularity = 1307.18.toFloat(),
            posterPath = "/xbKFv4KF3sVYuWKllLlwWDmuZP7.jpg",
            releaseDate = "2024-05-08",
            title = "IF",
            voteAverage = 7.469.toFloat(),
            voteCount = 407,
            isFavorite = false
        )
        `when`(nowPlayingRepository.getNowPlayingById(ID)).thenReturn(Flowable.just(ResultState.Success(dummyEntity)))
    }

    @Test
    fun `should get data from repository`() {
        val nowPlayingObserve = nowPlayingUseCase.getNowPlayingById(ID).test()

        nowPlayingObserve.assertSubscribed()
            .assertValue { resultState ->
                resultState is ResultState.Success && resultState.data == dummyEntity
            }
            .assertNoErrors()
            .assertComplete()

        verify(nowPlayingRepository).getNowPlayingById(ID)
    }

    companion object {
        const val ID = 10751
    }

}