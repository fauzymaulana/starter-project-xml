package com.papero.capstoneexpert.core.domain.mapper

import com.papero.capstoneexpert.core.data.source.local.entity.FavoriteEntityDB
import com.papero.capstoneexpert.core.data.source.local.entity.NowPlayingEntityDB
import com.papero.capstoneexpert.core.data.source.remote.genre.ItemGenreResponse
import com.papero.capstoneexpert.core.data.source.remote.now_playing.NowPlayingResponse
import com.papero.capstoneexpert.core.domain.model.now_playing.NowPlayingEntity

fun<T,R> List<T>?.toListEntity(): List<R> {
    val itemList = mutableListOf<R>()
    if (!this.isNullOrEmpty()) {
        this.forEach {
            when(it) {
                is NowPlayingEntityDB -> {
                    itemList.add(it.toEntity() as R)
                }

                is NowPlayingResponse -> {
                    itemList.add(it.toEntityDB() as R)
                }

                is NowPlayingEntity -> {
                    itemList.add(it.toEntityDB() as R)
                }

                is ItemGenreResponse -> {
                    itemList.add(it.toEntityDB() as R)
                }
                is FavoriteEntityDB -> {
                    itemList.add(it.toEntity() as R)
                }

//                is Int -> {
//                    itemList.add(it as Int)
//                }
//                is LogDataRealm -> {
//                    itemList.add(it.toDTO() as R)
//                }
//                is AnswerOnRealm -> {
//                    itemList.add(it.toDTO() as R)
//                }
//                is LogStatementAndMultipleCheckboxOptionRealm -> {
//                    itemList.add(it.toDTO() as R)
//                }
            }
        }
    }

    return itemList
}