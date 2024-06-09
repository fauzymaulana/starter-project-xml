package com.papero.capstoneexpert.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.core.ui.loadImageWithProgressBar
import com.papero.capstoneexpert.databinding.ItemNowPlayingLayoutBinding

class NowPlayingViewHolder(private val binding: ItemNowPlayingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: NowPlayingEntity) {
        binding.apply {
            ivThumbnail.loadImageWithProgressBar(movie.backdropPath, binding.progressBar)
            txtTitle.text = movie.title
            txtRating.text = countRound(movie.voteAverage)
            txtGenre.text = "Action"
        }
    }

    private fun countRound(value: Float?): String {
        return if (value != null) {
            "${Math.round(value * 10.0) / 10.0}"
        } else {
            ""
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