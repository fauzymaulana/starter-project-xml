package com.papero.capstoneexpert.presentation.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
            viewModel.apply {
                setMovieId(id)
                getMovieById()
            }
        }

        setInsetsScreen(binding.detaiRoot)
        initListeners()
        observe(viewModel.movie, ::observeMovieDetail)
    }

    fun setInsetsScreen(viewId: ConstraintLayout) {
        ViewCompat.setOnApplyWindowInsetsListener(viewId) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }
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
                    setContentImage(e.posterPath)
                    setTagAdult(e.adult)
                    setRating(e.voteAverage)
                    setReview(e.voteCount)
                    setTitle(e.title)
                    setPopularity(e.popularity)
                    setReleaseDate(e.releaseDate)
//                    setWebPage(e.)
                    setOverview(e.overview)

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

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setTitleToolbar("Detail Movie", true)
        (requireActivity() as MainActivity).showBottomNavigation(false)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.fab.id -> {
                Toast.makeText(context, "Ini Di Klik", Toast.LENGTH_SHORT).show()
            }
        }
    }
}