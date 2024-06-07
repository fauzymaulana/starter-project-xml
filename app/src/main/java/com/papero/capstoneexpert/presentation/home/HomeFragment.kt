package com.papero.capstoneexpert.presentation.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.mapper.toListEntity
import com.papero.capstoneexpert.core.domain.model.NowPlayingEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.databinding.FragmentHomeBinding
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
        viewModel.getNowPlaying()
    }
}