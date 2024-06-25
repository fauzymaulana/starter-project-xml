package com.papero.capstoneexpert.favorite.presentation

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.di.FavoriteModuleDependencies
import com.papero.capstoneexpert.favorite.R
import com.papero.capstoneexpert.favorite.databinding.FragmentFavoriteBinding
import com.papero.capstoneexpert.favorite.di.DaggerFavoriteComponent
import com.papero.capstoneexpert.favorite.presentation.FavoriteFragmentDirections
import com.papero.capstoneexpert.presentation.MainActivity
import com.papero.capstoneexpert.presentation.home.MovieAdapter
import com.papero.capstoneexpert.presentation.utilities.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }
    private var rvFavorite: RecyclerView? = null
    private var txtError: TextureView? = null

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            ).build().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getAllFavorite()
        observe(viewModel.favoriteList, ::observeFavoriteList)
    }

    private fun initViews() {
        rvFavorite  = view?.findViewById(R.id.rv_favorite)
        txtError    = view?.findViewById(R.id.label_empty)
    }

    private fun setupRecycler() {
        rvFavorite?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private val favoriteAdapter by lazy {
        MovieAdapter<FavoriteEntity>(OnClickListener {
            it.id?.let {id ->
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
                    viewModel.getAllFavorite()
                }
            }
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                if (resultState.data?.isNotEmpty() == true) {
                    rvFavorite?.visibility = View.VISIBLE
                    txtError?.visibility = View.GONE
                    favoriteAdapter.submitList(resultState.data)
                    setupRecycler()
                } else {
                    rvFavorite?.visibility = View.GONE
                    txtError?.visibility = View.VISIBLE
                }

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