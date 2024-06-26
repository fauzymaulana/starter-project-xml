package com.papero.capstoneexpert.presentation.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.databinding.FragmentHomeBinding
import com.papero.capstoneexpert.presentation.MainActivity
import com.papero.capstoneexpert.presentation.home.HomeFragmentDirections
import com.papero.capstoneexpert.presentation.utilities.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var queryFilter = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.nowPlaying, ::observeNowPlaying)
        queryFilter = arguments?.getString(QUERY_FILTER).toString()
    }

    private val nowPlayingAdapter by lazy {
        MovieAdapter<NowPlayingEntity>(OnClickListener {
            it.id?.let { id ->
                moveToDetail(id)
            }
        })
    }

    private fun setupRecycler() {
        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = nowPlayingAdapter

        }
    }

    private fun observeNowPlaying(resultState: ResultState<List<NowPlayingEntity>>) {
        when(resultState) {
            is ResultState.BadRequest -> {}
            is ResultState.Conflict -> {}
            is ResultState.Forbidden -> {}
            is ResultState.HideLoading -> {}
            is ResultState.Loading -> {
            }
            is ResultState.NoConnection -> {
                showSnackBarwithAction(
                    color = com.google.android.material.R.color.error_color_material_light,
                    message = resultState.message.toString(),
                    actionMessage = "Muat Ulang"
                ) {
                    viewModel.getNowPlaying()
                }
            }
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                nowPlayingAdapter.submitList(resultState.data)
                setupRecycler()
            }
            is ResultState.Timeout -> {}
            is ResultState.Unauthorized -> {}
            is ResultState.UnknownError -> {
                showSnackBarwithAction(
                    color = com.google.android.material.R.color.error_color_material_light,
                    message = resultState.message.toString(),
                    actionMessage = null
                ) {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setTitleToolbar("Now Playing", false)
        (requireActivity() as MainActivity).showBottomNavigation(true)
        viewModel.apply {
            getNowPlaying()
        }
    }

    private fun moveToDetail(id: Int) {
        val detail = HomeFragmentDirections.actionHomeToDetail()
        detail.movieId = id

        view?.findNavController()?.navigate(detail)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllGenre()
    }

    companion object {
        const val QUERY_FILTER = "queryFilter"
    }
}