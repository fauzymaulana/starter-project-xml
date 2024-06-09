package com.papero.capstoneexpert.presentation.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.databinding.FragmentHomeBinding
import com.papero.capstoneexpert.presentation.MainActivity
import com.papero.capstoneexpert.presentation.utilities.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


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
    }

    private val nowPlayingAdapter by lazy {
        MovieAdapter(OnClickListener {
            it.id?.let { id ->

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
                Log.e("TAG", "observeNowPlaying: Is Loading Active", )
            }
            is ResultState.NoConnection -> {}
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                nowPlayingAdapter.submitList(resultState.data)
                setupRecycler()
                Log.e("TAG", "observeNowPlaying: ini datanya ${resultState.data?.toList()}", )
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
        viewModel.apply {
            getNowPlaying()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllGenre()
    }
}