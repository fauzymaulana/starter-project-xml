package com.papero.capstoneexpert.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.ui.countRound
import com.papero.capstoneexpert.core.ui.loadImageWithProgressBar
import com.papero.capstoneexpert.databinding.ItemNowPlayingLayoutBinding

class NowPlayingViewHolder(private val binding: ItemNowPlayingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun <T>bind(movie: T) {
        when(movie) {
            is NowPlayingEntity -> {
                binding.apply {
                    ivThumbnail.loadImageWithProgressBar(movie.backdropPath, binding.progressBar)
                    txtTitle.text = movie.title
                    txtRating.text = countRound(movie.voteAverage)
                    txtGenre.text = "Action"
                }
            }
            is FavoriteEntity -> {
                binding.apply {
                    ivThumbnail.loadImageWithProgressBar(movie.backdropPath, binding.progressBar)
                    txtTitle.text = movie.title
                    txtRating.text = countRound(movie.voteAverage)
                    txtGenre.text = "Action"
                }
            }
        }
    }

    companion object {
        fun onCreate(view: ViewGroup) : NowPlayingViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = ItemNowPlayingLayoutBinding.inflate(inflater, view, false)
            return NowPlayingViewHolder(binding)
        }
    }
}