package com.papero.capstoneexpert.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity
import com.papero.capstoneexpert.presentation.utilities.OnClickListener

class MovieAdapter(val clickListener: OnClickListener<NowPlayingEntity>): ListAdapter<NowPlayingEntity,NowPlayingViewHolder>(
    COMPARATOR) {

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
        private val COMPARATOR = object : DiffUtil.ItemCallback<NowPlayingEntity>() {
            override fun areItemsTheSame(
                oldItem: NowPlayingEntity,
                newItem: NowPlayingEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NowPlayingEntity,
                newItem: NowPlayingEntity
            ): Boolean = oldItem == newItem

        }
    }
}