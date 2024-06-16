package com.papero.capstoneexpert.favorite.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.favorite.R
import com.papero.capstoneexpert.favorite.databinding.FragmentFavoriteBinding
import com.papero.capstoneexpert.favorite.presentation.FavoriteFragmentDirections
import com.papero.capstoneexpert.presentation.MainActivity
import com.papero.capstoneexpert.presentation.home.MovieAdapter
import com.papero.capstoneexpert.presentation.utilities.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {

    private val viewModel: FavoriteViewModel by viewModels()
//    private var _binding: FragmentFavoriteBinding? = null
//    private val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
//        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
//        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observe(viewModel.favoriteList, ::observeFavoriteList)
    }

    private val favoriteAdapter by lazy {
        MovieAdapter<FavoriteEntity>(OnClickListener {
            it.id?.let {
                moveToDetail(id)
            }
        })
    }

    private fun observeFavoriteList(resultState: ResultState<List<FavoriteEntity>>) {
        when(resultState) {
            is ResultState.BadRequest -> {}
            is ResultState.Conflict -> {}
            is ResultState.Forbidden -> {}
            is ResultState.HideLoading -> {}
            is ResultState.Loading -> {}
            is ResultState.NoConnection -> {
                showSnackBarwithAction(
                    color = com.google.android.material.R.color.error_color_material_light,
                    message = resultState.message.toString(),
                    actionMessage = "Muat Ulang"
                ) {
//                    viewModel.getAllFavorite()
                }
            }
            is ResultState.NotFound -> {}
            is ResultState.Success -> {

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
        (requireActivity() as MainActivity).setTitleToolbar("Local Movie", false)
        (requireActivity() as MainActivity).showBottomNavigation(true)
    }

    private fun moveToDetail(id: Int) {
        val detail = FavoriteFragmentDirections.actionFavoriteToDetail()
        detail.movieId = id

        view?.findNavController()?.navigate(detail)
    }
}