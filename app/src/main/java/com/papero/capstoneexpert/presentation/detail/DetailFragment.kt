package com.papero.capstoneexpert.presentation.detail

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.base.BaseFragment
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.ui.countRound
import com.papero.capstoneexpert.core.ui.loadImageWithProgressBar
import com.papero.capstoneexpert.core.ui.setContent
import com.papero.capstoneexpert.core.utilities.ResultState
import com.papero.capstoneexpert.core.utilities.observe
import com.papero.capstoneexpert.databinding.FragmentDetailBinding
import com.papero.capstoneexpert.presentation.MainActivity
import com.papero.capstoneexpert.presentation.utilities.GeneralPopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var detailMovie: NowPlayingEntity? = null

    private var toSave: Boolean = false
    private lateinit var dialogLoading: Dialog

//    private var searchView: SearchView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = DetailFragmentArgs.fromBundle(arguments as Bundle).movieId
        movieId.let { id ->
            Log.e("TAG", "onViewCreated: dat $id", )
            viewModel.apply {
                setMovieId(id)
//                getMovieById()
                fetchNowPlayingWithFavorite()
            }
        }

        setInsetsScreen(binding.detaiRoot)
        initViews()
        initListeners()
        observe(viewModel.movie, ::observeMovieDetail)
        observe(viewModel.foundMovie, ::observeFindFavorite)
        observe(viewModel.saveMovie, ::observeSaveMovie)
        observe(viewModel.deleteMovie, ::observeDeleteMovie)
    }

    private fun setInsetsScreen(viewId: ConstraintLayout) {
        ViewCompat.setOnApplyWindowInsetsListener(viewId) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
//        searchView = activity?.findViewById(R.id.searchView)
    }
    private fun initListeners() {
        binding.fab.setOnClickListener(this)
    }

    private fun observeMovieDetail(resultState: ResultState<NowPlayingEntity>) {
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
                    viewModel.getMovieById()
                }
            }
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                val data = resultState.data
                data?.let { e ->
                    detailMovie = e
                    setContentImage(e.posterPath)
                    setTagAdult(e.adult)
                    setRating(e.voteAverage)
                    setReview(e.voteCount)
                    setTitle(e.title)
                    setPopularity(e.popularity)
                    setReleaseDate(e.releaseDate)
//                    setWebPage(e.)
                    setOverview(e.overview)

                    binding.fab.icon = ResourcesCompat.getDrawable(
                        resources,
                        if (e.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline,
                        null
                    )
//                    binding.fab.image(R.drawable.ic_favorite_outline)
                    binding.fab.setTag(R.id.fab_icon_tag, if (e.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)
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

    private fun observeSaveMovie(resultState: ResultState<Long>) {
        when(resultState) {
            is ResultState.BadRequest -> {}
            is ResultState.Conflict -> {}
            is ResultState.Forbidden -> {}
            is ResultState.HideLoading -> {
                if (toSave) {
                    dismissLoading()
                }
            }
            is ResultState.Loading -> {}
            is ResultState.NoConnection -> {}
            is ResultState.NotFound -> {}
            is ResultState.Success ->{
                Toast.makeText(context, "Movie berhasil di simpan", Toast.LENGTH_SHORT).show()
                binding.fab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_filled, null)
                binding.fab.setTag(R.id.fab_icon_tag, R.drawable.ic_favorite_filled)
            }
            is ResultState.Timeout -> {}
            is ResultState.Unauthorized -> {}
            is ResultState.UnknownError -> {}
        }
    }

    private fun observeDeleteMovie(resultState: ResultState<Unit>) {
        when(resultState) {
            is ResultState.BadRequest -> {}
            is ResultState.Conflict -> {}
            is ResultState.Forbidden -> {}
            is ResultState.HideLoading -> {
                dismissLoading()
            }
            is ResultState.Loading -> {
                showLoading()
            }
            is ResultState.NoConnection -> {}
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                Toast.makeText(context, "Berhasil menghapus data", Toast.LENGTH_SHORT).show()
                binding.fab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_outline, null)
                binding.fab.setTag(R.id.fab_icon_tag, R.drawable.ic_favorite_outline)
            }
            is ResultState.Timeout -> {}
            is ResultState.Unauthorized -> {}
            is ResultState.UnknownError -> {}
        }
    }

    private fun observeFindFavorite(resultState: ResultState<Boolean>) {
        when(resultState) {
            is ResultState.BadRequest -> {}
            is ResultState.Conflict -> {}
            is ResultState.Forbidden -> {}
            is ResultState.HideLoading -> {
                if (!toSave) {
                    dismissLoading()
                }
            }
            is ResultState.Loading -> {
                showLoading()
            }
            is ResultState.NoConnection -> {}
            is ResultState.NotFound -> {}
            is ResultState.Success -> {
                resultState.data?.let { saved ->
                    if (saved) {
                        if (toSave) {
                            Toast.makeText(context, "Data sudah tersimpan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        if (toSave) {
                            detailMovie?.let { viewModel.addFavorite(it) }
                        }
                    }
                }
            }
            is ResultState.Timeout -> {}
            is ResultState.Unauthorized -> {}
            is ResultState.UnknownError -> {
                Log.e("TAG", "observeFindFavorite: ER ${resultState.message}", )
            }
        }
    }

    private fun setWebPage(value: String?) {
        value?.let { webPage ->
            binding.txtWebPage.setContent(webPage)
        }
    }

    private fun setReleaseDate(value: String?) {
        value?.let { releaseAt ->
            binding.txtRelease.setContent(releaseAt)
        }
    }

    private fun setPopularity(value: Float?) {
        value?.let { popularity ->
            binding.txtPopularity.setContent(popularity)
        }
    }

    private fun setOverview(value: String?) {
        value?.let { overview ->
            binding.txtOverview.setContent(overview)
        }
    }

    private fun setTitle(value: String?) {
        value?.let { title ->
            binding.txtTitle.setContent(title)
        }
    }

    private fun setReview(value: Int?) {
        value?.let { reviews ->
            binding.txtReviews.setContent("($reviews)")
        }
    }

    private fun setRating(value: Float?) {
        value?.let { rating ->
            binding.txtRating.setContent(countRound(rating))
        }
    }

    private fun setTagAdult(value: Boolean?) {
        value?.let { adult ->
            val color = if (adult) com.papero.capstoneexpert.core.R.color.red else com.papero.capstoneexpert.core.R.color.blue
            val title = if (adult) "18+" else "8+"

            binding.tagAdult.apply {
                backgroundTintList = resources.getColorStateList(color, null)
                text = title
            }
        }
    }

    private fun setContentImage(path: String?) {
        binding.ivThumbnail.loadImageWithProgressBar(
            url = path,
            progressBar = binding.pbThumbnail
        )
    }

    private fun showLoading() {
        GeneralPopUp(requireContext()).setupLoadingRounded { dialog ->
            dialogLoading = dialog
        }
    }

    private fun dismissLoading() {
        if (::dialogLoading.isInitialized && dialogLoading.isShowing) {
            dialogLoading.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setTitleToolbar("Detail Movie", true)
        (requireActivity() as MainActivity).showBottomNavigation(false)
        toSave = false
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.fab.id -> {
                Log.e("TAG", "onClick: di", )
//                when (binding.fab.icon) {
//                    ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_filled, null) -> {
//                        Log.e("TAG", "onClick: d", )
//                        viewModel.deleteMovieSaved()
//                    }
//                    ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_outline, null) -> {
//                        Log.e("TAG", "onClick: s", )
//                        toSave = true
//                        viewModel.getFavoriteById()
//                    }
//                }
                when(binding.fab.getTag(R.id.fab_icon_tag) as? Int) {
                    R.drawable.ic_favorite_filled -> {
                        Log.e("TAG", "onClick: d", )

                        viewModel.deleteMovieSaved()
                    }
                    R.drawable.ic_favorite_outline -> {
                        Log.e("TAG", "onClick: s", )
                        toSave = true
                        viewModel.getFavoriteById()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).findViewById<SearchView>(R.id.searchView).visibility = View.GONE
    }
}