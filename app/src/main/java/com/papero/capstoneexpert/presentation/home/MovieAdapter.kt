package com.papero.capstoneexpert.presentation.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.papero.capstoneexpert.core.domain.model.favorite.FavoriteEntity
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.presentation.utilities.OnClickListener

class MovieAdapter <T: Any> (private val clickListener: OnClickListener<T>): ListAdapter<T,NowPlayingViewHolder>(
    COMPARATOR as DiffUtil.ItemCallback<T>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        return NowPlayingViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            clickListener.onClick(data)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(
                oldItem: Any,
                newItem: Any
            ): Boolean {
                return when {
                    oldItem is NowPlayingEntity && newItem is NowPlayingEntity -> {
                        oldItem.id == newItem.id
                    }
                    oldItem is FavoriteEntity && newItem is FavoriteEntity -> {
                        oldItem.id == newItem.id
                    }
                    else -> {
                        oldItem == newItem
                    }
                }
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Any,
                newItem: Any
            ): Boolean {
                return when {
                    oldItem is NowPlayingEntity && newItem is NowPlayingEntity -> {
                        oldItem == newItem
                    }
                    oldItem is FavoriteEntity && newItem is FavoriteEntity -> {
                        oldItem == newItem
                    }

                    else -> { oldItem == newItem }
                }
            }
        }
    }
}