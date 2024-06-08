package com.papero.capstoneexpert.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.papero.capstoneexpert.R
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.databinding.ItemNowPlayingLayoutBinding

class NowPlayingViewHolder(private val binding: ItemNowPlayingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: NowPlayingEntity) {
        with(itemView) {
            Glide.with(context)
                .load(movie.backdropPath)
                .fitCenter()
                .error(R.drawable.ill_thumbnail_empty)
                .into(binding.ivThumbnail)

            binding.txtTitle.text = movie.title
            binding.apply {
                txtTitle.text = movie.title
                txtRating.text = movie.voteAverage.toString()
                val temp = mutableListOf<String>()
                txtGenre
            }
        }
    }
}